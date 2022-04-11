package rs.ac.uns.ftn.siit.isa_mrs.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.siit.isa_mrs.exception.UserNotFound;
import rs.ac.uns.ftn.siit.isa_mrs.model.Request;
import rs.ac.uns.ftn.siit.isa_mrs.model.User;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RequestStatus;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RequestType;
import rs.ac.uns.ftn.siit.isa_mrs.service.RequestService;
import rs.ac.uns.ftn.siit.isa_mrs.service.UserService;

import java.time.LocalDateTime;
import java.util.Optional;

import static rs.ac.uns.ftn.siit.isa_mrs.util.Paths.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(REQUEST_CONTROLLER)
public class RequestController {
    private final RequestService requestService;
    private final UserService userService;

    @PostMapping(DELETE_ACCOUNT)
    public void createRequest(@RequestBody RequestForm requestForm ){
        Request request = new Request();
        Optional<User> user = userService.getUser(requestForm.getEmail());
        if(user.isPresent()){
            request.setStatus(RequestStatus.Pending);
            request.setUser(user.get());
            request.setType(RequestType.valueOf(requestForm.getRequestType()));
            request.setTimeStamp(LocalDateTime.now());
            request.setReason(requestForm.enteredRequest);

            requestService.saveRequest(request);
        }else{
            throw new UserNotFound(HttpStatus.NOT_FOUND, "email: " + requestForm.getEmail());
        }
    }

    @Data
    class RequestForm{
        private String email;
        private String enteredRequest;
        private String requestType;

    }
}
