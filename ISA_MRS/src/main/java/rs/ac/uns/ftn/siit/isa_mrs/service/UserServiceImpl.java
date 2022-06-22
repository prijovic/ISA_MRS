package rs.ac.uns.ftn.siit.isa_mrs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.NewUserBasicInfoDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.UpdateUserDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.UserByTypeDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.UserDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.*;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.UserType;
import rs.ac.uns.ftn.siit.isa_mrs.repository.*;
import rs.ac.uns.ftn.siit.isa_mrs.security.JwtDecoder;
import rs.ac.uns.ftn.siit.isa_mrs.util.ObjectConverter;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JwtDecoder jwtDecoder;
    private final EmailSenderService emailSenderService;
    private final ObjectConverter objectConverter;
    private final RentalObjectOwnerRepo rentalObjectOwnerRepo;
    private final ClientRepo clientRepo;
    private final AdminRepo adminRepo;
    private final AddressRepo addressRepo;
    private final RentalObjectRepo rentalObjectRepo;

    @Override
    public ResponseEntity<Collection<UserDto>> getUsers() {
        try {
            Collection<User> users = userRepo.findAll();
            Collection<UserDto> userDtos = new ArrayList<>();
            users.forEach(user -> userDtos.add(modelMapper.map(user, UserDto.class)));
            return new ResponseEntity<>(userDtos, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<PageDto<UserDto>> getUsersPagination(int page, int pageSize, String token) {
        PageDto<UserDto> result = new PageDto<>();
        try{
            JwtDecoder.DecodedToken decodedToken;
            try {
                decodedToken = jwtDecoder.decodeToken(token);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            Pageable pageable = PageRequest.of(page, pageSize).withSort(Sort.by(Sort.Order.asc("name"), Sort.Order.asc("surname")));
            Page<User> usersPage = userRepo.findAll(pageable);
            Collection<UserDto> userDtos = new ArrayList<>();
            usersPage.getContent().forEach(user -> userDtos.add(mapUserToDto(user, decodedToken.getEmail())));
            result.setContent(userDtos);
            result.setPages(usersPage.getTotalPages());
            result.setCurrentPage(usersPage.getNumber());
            result.setPageSize(usersPage.getSize());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<UserDto> updateUserPassword(String email, String oldPassword, String newPassword) {
        try {
            Optional<User> userOptional = userRepo.findByEmail(email);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                if (passwordEncoder.matches(oldPassword, user.getPassword())) {
                    String encodedPassword = passwordEncoder.encode(newPassword);
                    user.setPassword(encodedPassword);
                    user.setFirstLogin(false);
                    userRepo.save(user);
                    UserDto userDto = modelMapper.map(user, UserDto.class);
                    return new ResponseEntity<>(userDto, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<UserDto> createUser(NewUserBasicInfoDto newUserInfo) {
        try {
            if (userRepo.findByEmail(newUserInfo.getEmail()).isPresent()) {
                return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
            }
            Address address = modelMapper.map(newUserInfo.getAddress(), Address.class);
            addressRepo.save(address);
            User user = objectConverter.getUserFromNewUserData(newUserInfo, address);
            String password = generatePassayPassword();
            user.setPassword(passwordEncoder.encode(password));
            user.setFirstLogin(true);
            if (newUserInfo.getUserType().equals(UserType.Client)) {
                clientRepo.save((Client) user);
            }
            else if (newUserInfo.getUserType().equals(UserType.Admin)) {
                adminRepo.save((Admin) user);
            }
            else {
                rentalObjectOwnerRepo.save((RentalObjectOwner) user);
            }
            UserDto userDto = modelMapper.map(user, UserDto.class);
            emailSenderService.sendActivationNotificationEmail(user, password);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
            searchResult.setIsActive(!searchResult.getIsActive());
            userRepo.save(searchResult);
            UserDto userDto = modelMapper.map(searchResult, UserDto.class);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }
        catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Collection<UserDto>> changeUsersStatus(Collection<Long> ids) {
        Collection<Long> changedStatuses = new ArrayList<>();
        try {
            Collection<UserDto> result = new ArrayList<>();
            ids.forEach(id -> {
                User user = userRepo.getById(id);
                user.setIsActive(!user.getIsActive());
                userRepo.save(user);
                changedStatuses.add(id);
                result.add(modelMapper.map(user, UserDto.class));
            });
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            changeUsersStatus(changedStatuses);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
                if (user.getIsActive()) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
                user.setIsActive(true);
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

    @Override
    public ResponseEntity<NewUserBasicInfoDto> getUser(String token) {
        try {
            JwtDecoder.DecodedToken decodedToken;
            try {
                decodedToken = jwtDecoder.decodeToken(token);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            Optional<User> user = userRepo.findByEmail(decodedToken.getEmail());
            if (user.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(modelMapper.map(user.get(), NewUserBasicInfoDto.class), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<NewUserBasicInfoDto> updateUser(NewUserBasicInfoDto newUserBasicInfoDto) {
        try {
            Optional<User> user = userRepo.findByEmail(newUserBasicInfoDto.getEmail());
            if (user.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            User u = user.get();
            u.setName(newUserBasicInfoDto.getName());
            u.setSurname(newUserBasicInfoDto.getSurname());
            Address address = modelMapper.map(newUserBasicInfoDto.getAddress(), Address.class);
            addressRepo.save(address);
            u.setAddress(address);
            u.setPhone(newUserBasicInfoDto.getPhone());
            userRepo.save(u);
            return new ResponseEntity<>(modelMapper.map(u, NewUserBasicInfoDto.class), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String generatePassayPassword() {
        String upperCaseLetters = RandomStringUtils.random(2, 65, 90, true, true);
        String lowerCaseLetters = RandomStringUtils.random(2, 97, 122, true, true);
        String numbers = RandomStringUtils.randomNumeric(2);
        String specialChar = RandomStringUtils.random(2, 33, 47, false, false);
        String totalChars = RandomStringUtils.randomAlphanumeric(2);
        String combinedChars = upperCaseLetters.concat(lowerCaseLetters)
                .concat(numbers)
                .concat(specialChar)
                .concat(totalChars);
        List<Character> pwdChars = combinedChars.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        Collections.shuffle(pwdChars);
        return pwdChars.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    private UserDto mapUserToDto(User user, String email) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        if (user.getEmail().equals(email) || user.getUserType().equals(UserType.SuperAdmin)) {
            userDto.setIsDeletable(false);
        } else if (user.getUserType().equals(UserType.Client)) {
            Client client = clientRepo.getById(user.getId());
            userDto.setIsDeletable(isClientDeletable(client));
        } else if (user.getUserType().equals(UserType.VacationRentalOwner) ||
                user.getUserType().equals(UserType.BoatOwner) ||
                user.getUserType().equals(UserType.Instructor)) {
            RentalObjectOwner owner = rentalObjectOwnerRepo.getById(user.getId());
            userDto.setIsDeletable(isRentalOwnerDeletable(owner));
        }
        return userDto;
    }

    private boolean isRentalOwnerDeletable(RentalObjectOwner owner) {
        boolean rentalObjectOwnerIsDeletable = true;
        Collection<RentalObject> rentalObjects = rentalObjectRepo.findAllByRentalObjectOwner(owner);
        for (RentalObject rentalObject : rentalObjects) {
            for (Reservation reservation : rentalObject.getReservations()) {
                if (reservation.getInitDate().isAfter(ChronoLocalDateTime.from(LocalDateTime.now()))) {
                    rentalObjectOwnerIsDeletable = false;
                    break;
                }
                if (!rentalObjectOwnerIsDeletable) {
                    break;
                }
            }
        }
        return rentalObjectOwnerIsDeletable;
    }

    private boolean isClientDeletable(Client client) {
        boolean clientIsDeletable = true;
        for (Reservation reservation : client.getReservations()) {
            if (reservation.getInitDate().isAfter(ChronoLocalDateTime.from(LocalDateTime.now()))) {
                clientIsDeletable = false;
                break;
            }
            if (!clientIsDeletable) {
                break;
            }
        }
        return clientIsDeletable;
    }
}
