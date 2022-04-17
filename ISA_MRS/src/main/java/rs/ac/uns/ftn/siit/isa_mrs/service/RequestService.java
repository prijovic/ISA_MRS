package rs.ac.uns.ftn.siit.isa_mrs.service;

import org.springframework.http.ResponseEntity;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.RequestDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.RespondedRequestDto;


public interface RequestService {
    ResponseEntity<PageDto<RequestDto>> findRequestsWithPaginationSortedByField(int offset, int pageSize, String types, String field);
    ResponseEntity<RespondedRequestDto> changeRequestStatus(Long id, String status, String reason, String adminEmail);

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import rs.ac.uns.ftn.siit.isa_mrs.dto.RequestDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.Request;

import java.util.List;
import java.util.Optional;

public interface RequestService {
    Page<Request> findRequestsWithPaginationSortedByField(int offset, int pageSize, String field);
    ResponseEntity<RequestDto> createRequest(String email, String password, String reason, String requestType);
}
