package rs.ac.uns.ftn.siit.isa_mrs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.isa_mrs.dto.UserDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.User;
import rs.ac.uns.ftn.siit.isa_mrs.repository.UserRepo;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

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
    public ResponseEntity<User> getUser(String email, String password) {
        try{
            Optional<User> user = userRepo.findByEmailAndPassword(email, password);
            return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
        } catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
