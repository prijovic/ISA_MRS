package rs.ac.uns.ftn.siit.isa_mrs.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.siit.isa_mrs.exception.InvalidPassword;
import rs.ac.uns.ftn.siit.isa_mrs.exception.UserNotFound;
import rs.ac.uns.ftn.siit.isa_mrs.model.User;
import rs.ac.uns.ftn.siit.isa_mrs.service.UserService;

import java.util.*;

import static rs.ac.uns.ftn.siit.isa_mrs.util.Paths.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(USER_CONTROLLER)
public class UserController {
    private final UserService userService;

    @PutMapping(PASSWORD_CHANGE)
    public void updateUserPassword(@RequestBody PasswordChangeForm form) {
        Optional<User> user = userService.getUser(form.getEmail());
        if (user.isPresent()){
            if (user.get().getPassword().equals(form.getOldPassword())){
                userService.updateUserPassword(form.getEmail(), form.getNewPassword());
            }
            else {
                throw new InvalidPassword(HttpStatus.NON_AUTHORITATIVE_INFORMATION, "Old password is not valid.");
            }
        }
        else{
            throw new UserNotFound(HttpStatus.NOT_FOUND, "email: " + form.getEmail());
        }
    }

    @DeleteMapping(DELETE_USER)
    public void deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
    }

    @Data
    class PasswordChangeForm {
        private String email;
        private String oldPassword;
        private String newPassword;
    }
}
