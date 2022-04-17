package rs.ac.uns.ftn.siit.isa_mrs.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.siit.isa_mrs.dto.RequestDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.Request;
import rs.ac.uns.ftn.siit.isa_mrs.service.RequestService;

import java.util.ArrayList;
import java.util.Collection;


import static rs.ac.uns.ftn.siit.isa_mrs.util.Paths.DELETE_ACCOUNT;
import static rs.ac.uns.ftn.siit.isa_mrs.util.Paths.REQUEST_CONTROLLER;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(REQUEST_CONTROLLER)
public class RequestController {
    private final RequestService requestService;
    private final ModelMapper modelMapper;

    @GetMapping("/requests")
    public @ResponseBody Collection<RequestDto> getRequestsWithPaginationAndSort(@RequestParam Integer offset, @RequestParam Integer pageSize, @RequestParam String field) {
        log.info("Requests are coming...");
        Collection<RequestDto> finalResult = new ArrayList<>();
        Page<Request> requestsWithPagination = requestService.findRequestsWithPaginationSortedByField(offset, pageSize, field);
        Collection<Request> result = requestsWithPagination.getContent();
        result.forEach(request -> finalResult.add(modelMapper.map(request, RequestDto.class)));
        return finalResult;
    }

    @PostMapping(DELETE_ACCOUNT)
    public ResponseEntity<RequestDto> createDeleteAccountRequest(@RequestBody AccountDeletionRequestForm requestForm){
        return requestService.createRequest(requestForm.getEmail(), requestForm.getPassword(), requestForm.getEnteredRequest(), requestForm.getRequestType());
    }

    @Data
    static class AccountDeletionRequestForm{
        private String email;
        private String password;
        private String enteredRequest;
        private String requestType;

    }

}
