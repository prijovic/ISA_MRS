package rs.ac.uns.ftn.siit.isa_mrs.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.IdListWrapperClass;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.NewUserBasicInfoDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.UserByTypeDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.UserDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.UserType;
import rs.ac.uns.ftn.siit.isa_mrs.service.UserService;

import javax.servlet.http.HttpServletRequest;

import java.util.Collection;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static rs.ac.uns.ftn.siit.isa_mrs.util.Paths.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(USER_CONTROLLER)
public class UserController {
    private final UserService userService;

    @GetMapping("/getUsers")
    public ResponseEntity<Collection<UserDto>> getAllUsers() {
        return userService.getUsers();
    }

    @GetMapping("/getUsersPage")
    public ResponseEntity<PageDto<UserDto>> getUsersWithPaginationAndSort(
            @RequestParam Integer page, @RequestParam Integer pageSize, HttpServletRequest request) {
        return userService.getUsersPagination(page, pageSize, request.getHeader(AUTHORIZATION));
    }

    @PostMapping("/addUser")
    public ResponseEntity<UserDto> addUser(@RequestBody NewUserBasicInfoDto newUserBasicInfoDto) {
        return userService.createUser(newUserBasicInfoDto);
    }

    @PutMapping(PASSWORD_CHANGE)
    public ResponseEntity<UserDto> updateUserPassword(@RequestBody PasswordChangeForm form) {
        return userService.updateUserPassword(form.getEmail(), form.getOldPassword(), form.getNewPassword());
    }

    @CrossOrigin(CROSS_ORIGIN)
    @PutMapping("/activate")
    public ResponseEntity<String> activateUser(HttpServletRequest request) {
        return userService.activateUser(request.getHeader(AUTHORIZATION));
    }

    @CrossOrigin(CROSS_ORIGIN)
    @PostMapping("/resendVerification")
    public ResponseEntity<String> resendVerification(HttpServletRequest request) {
        return userService.resendVerificationMail(request.getHeader(AUTHORIZATION));
    }

    @PutMapping(STATUS_CHANGE)
    public ResponseEntity<UserDto> changeUserStatus(@RequestParam Long id) {
        return userService.changeUserStatus(id);
    }

    @PutMapping("/multipleUserStatusChange")
    public ResponseEntity<Collection<UserDto>> changeUsersStatus(@RequestBody IdListWrapperClass lwc){
        return userService.changeUsersStatus(lwc.getList());
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
