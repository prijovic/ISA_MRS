package rs.ac.uns.ftn.siit.isa_mrs.service;

import org.springframework.http.ResponseEntity;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.SignUpDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.RequestDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.RespondedRequestDto;

public interface RequestService {
    ResponseEntity<RequestDto> createRequest(String email, String password, String reason, String requestType);
    ResponseEntity<PageDto<RequestDto>> findRequestsWithPaginationSortedByField(int offset, int pageSize, String types, String field);
    ResponseEntity<RespondedRequestDto> changeRequestStatus(Long id, String status, String reason, String adminEmail);
    ResponseEntity<RequestDto> createSignUpRequest(SignUpDto sud);
}
