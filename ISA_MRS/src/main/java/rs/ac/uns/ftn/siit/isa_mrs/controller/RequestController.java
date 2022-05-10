package rs.ac.uns.ftn.siit.isa_mrs.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.SignUpDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.RequestDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.RespondedRequestDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.Address;
import rs.ac.uns.ftn.siit.isa_mrs.model.User;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.UserType;
import rs.ac.uns.ftn.siit.isa_mrs.repository.UserRepo;
import rs.ac.uns.ftn.siit.isa_mrs.service.RequestService;
import rs.ac.uns.ftn.siit.isa_mrs.service.UserService;

import java.util.Optional;

import static rs.ac.uns.ftn.siit.isa_mrs.util.Paths.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(REQUEST_CONTROLLER)
public class RequestController {
    private final RequestService requestService;
    private final UserRepo userRepo;

    @GetMapping("/requests")
    public ResponseEntity<PageDto<RequestDto>> getRequestsWithPaginationAndSort(@RequestParam Integer page, @RequestParam Integer pageSize,
                                                                                @RequestParam String types, @RequestParam String field) {
        return requestService.findRequestsWithPaginationSortedByField(page, pageSize, types, field);
    }

    @PutMapping("/request")
    public ResponseEntity<RespondedRequestDto> changeRequestStatus(@RequestParam Long id, @RequestParam String status, @RequestParam String reason, @RequestParam String adminEmail) {
        return requestService.changeRequestStatus(id, status, reason, adminEmail);
    }

    @PostMapping(DELETE_ACCOUNT)
    public ResponseEntity<RequestDto> createDeleteAccountRequest(@RequestBody AccountDeletionRequestForm requestForm){
        return requestService.createRequest(requestForm.getEmail(), requestForm.getPassword(), requestForm.getEnteredRequest(), requestForm.getRequestType());
    }

    @PostMapping(value = SIGN_UP)
    public ResponseEntity<RequestDto> createSignUpRequest(@RequestBody SignUpDto sud) {
        return requestService.createSignUpRequest(sud);
    }

    @Data
    static class AccountDeletionRequestForm{
        private String email;
        private String password;
        private String enteredRequest;
        private String requestType;
    }
}
