package rs.ac.uns.ftn.siit.isa_mrs.service;

import org.springframework.http.ResponseEntity;
import rs.ac.uns.ftn.siit.isa_mrs.dto.UserDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.User;

public interface UserService {
    ResponseEntity<UserDto> updateUserPassword(String email, String oldPassword, String newPassword);
    void updateUserPassword(Long id, String newPassword);
    ResponseEntity<UserDto> changeUserStatus(Long id);
    ResponseEntity<UserDto> addNewUser(User user);
}
