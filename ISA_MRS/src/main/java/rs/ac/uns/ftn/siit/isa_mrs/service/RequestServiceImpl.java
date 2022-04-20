package rs.ac.uns.ftn.siit.isa_mrs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.User;
import rs.ac.uns.ftn.siit.isa_mrs.dto.RequestDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.RespondedRequestDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.Admin;
import rs.ac.uns.ftn.siit.isa_mrs.model.Request;
import rs.ac.uns.ftn.siit.isa_mrs.model.RequestResponse;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RequestStatus;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RequestType;
import rs.ac.uns.ftn.siit.isa_mrs.repository.AdminRepo;
import rs.ac.uns.ftn.siit.isa_mrs.repository.RequestRepo;
import rs.ac.uns.ftn.siit.isa_mrs.repository.RequestResponseRepo;
import rs.ac.uns.ftn.siit.isa_mrs.repository.UserRepo;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class RequestServiceImpl implements RequestService {
    private final RequestRepo requestRepo;
    private final RequestResponseRepo requestResponseRepo;
    private final AdminRepo adminRepo;
    private final ModelMapper modelMapper;
    private final EmailSenderService emailSenderService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    @Override
    public ResponseEntity<PageDto<RequestDto>> findRequestsWithPaginationSortedByField(int offset, int pageSize, String types, String field) {
        PageDto<RequestDto> result = new PageDto<>();
        try{
            Pageable pageable = PageRequest.of(offset, pageSize).withSort(Sort.by(field));
            Page<Request> requestPage;
            if (types.equals("all")) {
                requestPage = requestRepo.findALLByStatus(RequestStatus.Pending, pageable);
            }
            else {
                requestPage = requestRepo.findAllByTypeAndStatus(RequestType.valueOf(types), RequestStatus.Pending, pageable);
            }
            Collection<RequestDto> requestDtos = new ArrayList<>();
            requestPage.getContent().forEach(request -> requestDtos.add(modelMapper.map(request, RequestDto.class)));
            result.setContent(requestDtos);
            result.setPages(requestPage.getTotalPages());
            result.setCurrentPage(requestPage.getNumber() + 1);
            result.setPageSize(requestPage.getSize());
            if (requestPage.getContent().isEmpty()) {
                return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
            }
            else {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<RespondedRequestDto> changeRequestStatus(Long id, String status, String reason, String adminEmail) {
        try {
            Request request = requestRepo.getById(id);
            log.info(request.getId() + ": " + request.getStatus());
            RequestResponse requestResponse = new RequestResponse();
            requestResponse.setComment(reason);
            requestResponse.setTimeStamp(LocalDateTime.now());
            Admin admin = adminRepo.getByEmail(adminEmail);
            requestResponse.setOperator(admin);
            request.setStatus(RequestStatus.valueOf(status));
            requestRepo.save(request);
            requestResponse.setRequest(request);
            requestResponseRepo.save(requestResponse);
            request.setResponse(requestResponse);
            requestRepo.save(request);
            emailSenderService.sendRequestHandledEmail(request, createRequestResponseMailModel(request, requestResponse));
            RespondedRequestDto requestDto = modelMapper.map(requestRepo.getById(id), RespondedRequestDto.class);
            return new ResponseEntity<>(requestDto, HttpStatus.OK);
        }
        catch (EntityNotFoundException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<RequestDto> createRequest(String email, String password, String reason, String requestType){
        log.info("E-mail: {}", email);
        log.info("Password: {}", password);
        Optional<User> user = userRepo.findByEmail(email);
        if (user.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        else if (!passwordEncoder.matches(password, user.get().getPassword())) {
            log.info(user.get().getPassword());
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        else {
            try {
                User userValue = user.get();
                Request request = new Request();
                request.setReason(reason);
                request.setType(RequestType.valueOf(requestType));
                request.setTimeStamp(LocalDateTime.now());
                request.setUser(user.get());
                requestRepo.save(request);
                userValue.setRequest(request);
                userRepo.save(userValue);
                RequestDto requestDto = modelMapper.map(request, RequestDto.class);
                return new ResponseEntity<>(requestDto, HttpStatus.OK);
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    private Map<String, Object> createRequestResponseMailModel(Request request, RequestResponse response) {
        Map<String, Object> model = new HashMap<>();
        model.put("status", request.getStatus().toString().toLowerCase());
        model.put("date", request.getTimeStamp().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")));
        model.put("time", request.getTimeStamp().format(DateTimeFormatter.ofPattern("HH:mm")));
        model.put("requestType", String.join(" ", request.getType().toString().split("(?=\\p{Upper})")));
        model.put("response", request.getStatus().toString().toLowerCase());
        model.put("explanation", response.getComment());
        model.put("adminMail", response.getOperator().getEmail());
        return model;
    }
}
