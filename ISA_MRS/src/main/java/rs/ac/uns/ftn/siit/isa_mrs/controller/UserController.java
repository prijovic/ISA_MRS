package rs.ac.uns.ftn.siit.isa_mrs.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.RentalObjectOwnerDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.UserByTypeDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.UserDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.UserType;
import rs.ac.uns.ftn.siit.isa_mrs.service.UserService;

import static rs.ac.uns.ftn.siit.isa_mrs.util.Paths.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(USER_CONTROLLER)
public class UserController {
    private final UserService userService;

    @PutMapping(PASSWORD_CHANGE)
    public ResponseEntity<UserDto> updateUserPassword(@RequestBody PasswordChangeForm form) {
        return userService.updateUserPassword(form.getEmail(), form.getOldPassword(), form.getNewPassword());
    }

    @PutMapping(STATUS_CHANGE)
    public ResponseEntity<UserDto> changeUserStatus(@RequestParam Long id) {
        return userService.changeUserStatus(id);
    }

    @GetMapping("/getUserType")
    public ResponseEntity<PageDto<UserByTypeDto>> getUsersByTypeWithPaginationAndSort(
            @RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam String field,
            @RequestParam UserType type) {
        log.info("Usli smo");
        return userService.findUsersByTypeWithPaginationSortedByField(page, pageSize, field, type);
    }

    @Data
    static class PasswordChangeForm {
        private String email;
        private String oldPassword;
        private String newPassword;
    }
}
