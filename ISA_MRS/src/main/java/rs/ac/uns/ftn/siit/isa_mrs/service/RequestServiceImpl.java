package rs.ac.uns.ftn.siit.isa_mrs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.siit.isa_mrs.dto.RequestDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.Request;
import rs.ac.uns.ftn.siit.isa_mrs.model.User;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RequestType;
import rs.ac.uns.ftn.siit.isa_mrs.repository.RequestRepo;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RequestServiceImpl implements RequestService {
    private final RequestRepo requestRepo;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Override
    public Page<Request> findRequestsWithPaginationSortedByField(int offset, int pageSize, String field) {
        return requestRepo.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
    }

    @Override
    public ResponseEntity<RequestDto> createRequest(String email, String password, String reason, String requestType){
        ResponseEntity<User> userResponseEntity = userService.getUser(email, password);
        if (userResponseEntity.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        if (!userResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return new ResponseEntity<>(null, userResponseEntity.getStatusCode());
        }
        else {
            try {
                Request request = new Request();
                request.setReason(reason);
                request.setType(RequestType.valueOf(requestType));
                request.setTimeStamp(LocalDateTime.now());
                request.setUser(userResponseEntity.getBody());
                requestRepo.save(request);
                RequestDto requestDto = modelMapper.map(request, RequestDto.class);
                return new ResponseEntity<>(requestDto, HttpStatus.OK);
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
}
