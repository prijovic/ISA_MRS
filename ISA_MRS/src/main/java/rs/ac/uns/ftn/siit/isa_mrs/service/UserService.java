package rs.ac.uns.ftn.siit.isa_mrs.service;

import org.springframework.http.ResponseEntity;
import rs.ac.uns.ftn.siit.isa_mrs.dto.UserDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.User;

import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    void deleteUser(Long id);
    Optional<User> getUser(Long id);
    Optional<User> getUser(String email);
    ResponseEntity<User> getUser(String email, String password);
    void updateUserPassword(Long id, String password);
    void updateUserPassword(String email, String password);
}
