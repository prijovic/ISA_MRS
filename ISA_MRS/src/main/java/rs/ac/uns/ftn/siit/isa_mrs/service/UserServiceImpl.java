package rs.ac.uns.ftn.siit.isa_mrs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.siit.isa_mrs.dto.UserDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.User;
import rs.ac.uns.ftn.siit.isa_mrs.repository.UserRepo;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userResult = userRepo.findByEmail(email);
        if (userResult.isEmpty()) {
            log.info("User with email {} was not found in the database.", email);
            throw new UsernameNotFoundException("User with email " + email+ " was not found in the database.");
        }
        User user = userResult.get();
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getUserType().name()));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    @Override
    public User saveUser(User user) {
        log.info("Adding new user with id {}.", user.getId());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public Optional<User> getUser(Long id) {
        log.info("Getting user by id {}.", id);
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> getUser(String email) {
        log.info("Getting user by email {}.", email);
        return userRepo.findByEmail(email);
    }

    @Override
    public void deleteUser(Long id) {
        log.info("Deleting user with id {}", id);
        userRepo.deleteById(id);
    }

    @Override
    public void updateUserPassword(Long id, String password) {
        log.info("Changing user's password with id {} to {}.", id, password);
        Optional<User> user = userRepo.findById(id);
        user.ifPresent(value -> value.setPassword(passwordEncoder.encode(password)));
    }

    @Override
    public void updateUserPassword(String email, String password) {
        log.info("Changing user's password with email {} to {}.", email, password);
        Optional<User> user = userRepo.findByEmail(email);
        user.ifPresent(value -> value.setPassword(passwordEncoder.encode(password)));
    }

}
