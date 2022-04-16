package rs.ac.uns.ftn.siit.isa_mrs.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.siit.isa_mrs.dto.UserDto;
import rs.ac.uns.ftn.siit.isa_mrs.service.UserService;

import static rs.ac.uns.ftn.siit.isa_mrs.util.Paths.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(USER_CONTROLLER)
public class UserController {
    private final UserService userService;

    @CrossOrigin(origins = CROSS_ORIGIN)
    @PutMapping(PASSWORD_CHANGE)
    public ResponseEntity<UserDto> updateUserPassword(@RequestBody @NotNull PasswordChangeForm form) {
        return userService.updateUserPassword(form.getEmail(), form.getOldPassword(), form.getNewPassword());
    }

    @PutMapping(STATUS_CHANGE)
    public ResponseEntity<UserDto> changeUserStatus(@RequestParam Long id) {
        return userService.changeUserStatus(id);
    }

    @Data
    static class PasswordChangeForm {
        private String email;
        private String oldPassword;
        private String newPassword;
    }
}
