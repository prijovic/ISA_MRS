package rs.ac.uns.ftn.siit.isa_mrs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.UserByTypeDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.UserDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.User;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.UserType;
import rs.ac.uns.ftn.siit.isa_mrs.repository.UserRepo;
import rs.ac.uns.ftn.siit.isa_mrs.security.JwtDecoder;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JwtDecoder jwtDecoder;
    private final EmailSenderService emailSenderService;

    @Override
    public ResponseEntity<UserDto> updateUserPassword(String email, String oldPassword, String newPassword) {
        try {
            Optional<User> userOptional = userRepo.findByEmail(email);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                if (passwordEncoder.matches(oldPassword, user.getPassword())) {
                    String encodedPassword = passwordEncoder.encode(newPassword);
                    user.setPassword(encodedPassword);
                    userRepo.save(user);
                    UserDto userDto = modelMapper.map(user, UserDto.class);
                    return new ResponseEntity<>(userDto, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
                }
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void encryptUsersPasswords() {
        Collection<User> users = userRepo.findAll();
        users.forEach((user) -> {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.save(user);
        });
    }

    @Override
    public ResponseEntity<UserDto> changeUserStatus(Long id) {
        try {
            User searchResult = userRepo.getById(id);
            searchResult.setActive(!searchResult.isActive());
            userRepo.save(searchResult);
            UserDto userDto = modelMapper.map(searchResult, UserDto.class);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }
        catch (EntityNotFoundException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> activateUser(String token) {
        JwtDecoder.DecodedToken decodedToken;
        try {
            decodedToken = jwtDecoder.decodeToken(token);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            String email = decodedToken.getEmail();
            Optional<User> userResult = userRepo.findByEmail(email);
            if (userResult.isPresent()) {
                User user = userResult.get();
                if (user.isActive()) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
                user.setActive(true);
                userRepo.save(user);
                return new ResponseEntity<>(email, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> resendVerificationMail(String refresh_token) {
        JwtDecoder.DecodedToken decodedToken;
        try {
            decodedToken = jwtDecoder.decodeToken(refresh_token);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            String email = decodedToken.getEmail();
            Optional<User> userResult = userRepo.findByEmail(email);
            if (userResult.isPresent()) {
                User user = userResult.get();
                emailSenderService.sendActivationEmail(user);
                return new ResponseEntity<>(email, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @Override
//    public ResponseEntity<UserDto> addNewUser(User user) {
//        try {
//            if (user.getUserType().equals(UserType.Admin)) {
//                user.setActive(true);
//            }
//            else {
//                user.setActive(false);
//            }
//            userRepo.save(user);
//            UserDto userDto = modelMapper.map(user, UserDto.class);
//            return new ResponseEntity<>(userDto, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//
//        }
//    }

    @Override
    public ResponseEntity<PageDto<UserByTypeDto>> findUsersByTypeWithPaginationSortedByField(int offset,
                                                                         int pageSize, String field, UserType type) {
        PageDto<UserByTypeDto> result = new PageDto<>();
        try{
            Pageable pageable = PageRequest.of(offset, pageSize).withSort(Sort.by(field));
            log.info("Trazimo ownere");
            Page<User> usersPage = userRepo.findAllByUserType(type, pageable);
            log.info("Pronasli smo ownere: {}", usersPage.getNumberOfElements());
            Collection<UserByTypeDto> userDtos = new ArrayList<>();
            usersPage.getContent().forEach(user -> {
                userDtos.add(modelMapper.map(user, UserByTypeDto.class));
                log.info("Nasli smo ownera: {}", user.getId());
            });
            result.setContent(userDtos);
            result.setPages(usersPage.getTotalPages());
            result.setCurrentPage(usersPage.getNumber() + 1);
            result.setPageSize(usersPage.getSize());
            if (usersPage.getContent().isEmpty()) {
                log.info("Status: no content");
                return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
            }
            else {
                log.info("Status: ok");
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }
}
