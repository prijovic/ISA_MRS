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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.SignUpDtos.SignUpDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.*;
import rs.ac.uns.ftn.siit.isa_mrs.dto.RequestDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.RespondedRequestDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RequestStatus;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RequestType;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.UserType;
import rs.ac.uns.ftn.siit.isa_mrs.repository.*;
import rs.ac.uns.ftn.siit.isa_mrs.util.ObjectConverter;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepo requestRepo;
    private final RequestResponseRepo requestResponseRepo;
    private final AdminRepo adminRepo;
    private final ModelMapper modelMapper;
    private final EmailSenderService emailSenderService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final AddressRepo addressRepo;
    private final RentalObjectOwnerRepo rentalOwnerRepo;
    private final ClientRepo clientRepo;
    private final ObjectConverter objectConverter;

    @Override
    @Transactional(readOnly = false)
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
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public ResponseEntity<RespondedRequestDto> changeRequestStatus(Long id, String status, String reason, String adminEmail) {
        try {
            Request request = requestRepo.getById(id);
            if (!request.getStatus().equals(RequestStatus.Pending)) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
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
            User user = request.getUser();
            RespondedRequestDto requestDto = modelMapper.map(requestRepo.getById(id), RespondedRequestDto.class);
            if (request.getType().equals(RequestType.AccountDeletion)) {
                user.setIsActive(false);
            }
            else if (request.getType().equals(RequestType.SignUp)){
                emailSenderService.sendActivationEmail(user);
                return new ResponseEntity<>(requestDto, HttpStatus.OK);
            }
            userRepo.save(user);
            emailSenderService.sendRequestHandledEmail(request, createRequestResponseMailModel(request, requestResponse));
            return new ResponseEntity<>(requestDto, HttpStatus.OK);
        }
        catch (EntityNotFoundException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<RequestDto> createRequest(String email, String password, String reason, String requestType){
        Optional<User> user = userRepo.findByEmail(email);
        if (user.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else if (!passwordEncoder.matches(password, user.get().getPassword())) {
            log.info(user.get().getPassword());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        else {
            try {
                User userValue = user.get();
                Request request = new Request();
                request.setStatus(RequestStatus.Pending);
                request.setReason(reason);
                request.setType(RequestType.valueOf(requestType));
                request.setTimeStamp(LocalDateTime.now());
                request.setUser(userValue);
                requestRepo.save(request);
                userValue.setRequest(request);
                userRepo.save(userValue);
                RequestDto requestDto = modelMapper.map(request, RequestDto.class);
                return new ResponseEntity<>(requestDto, HttpStatus.OK);
            } catch (IllegalArgumentException e) {
                log.error(e.getMessage());
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                log.error(e.getMessage());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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

    @Override
    public ResponseEntity<RequestDto> createSignUpRequest(SignUpDto signUpRequestData) {
        Optional<User> user = userRepo.findByEmail(signUpRequestData.getEmail());
        if (user.isPresent()){
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        else {
            try {
                Address address = modelMapper.map(signUpRequestData.getAddress(), Address.class);
                addressRepo.save(address);
                if(signUpRequestData.getUserType().equals(UserType.Client)) {
                    Client client = (Client) objectConverter.getUserFromSignUpRequestData(signUpRequestData, address);
                    clientRepo.save(client);
                    emailSenderService.sendActivationEmail(client);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
                else {
                    RentalObjectOwner owner = (RentalObjectOwner) objectConverter.getUserFromSignUpRequestData(signUpRequestData, address);
                    rentalOwnerRepo.save(owner);
                    Request request = objectConverter.getRequestFromSignUpRequestData(signUpRequestData, owner);
                    requestRepo.save(request);
                    RequestDto requestDto = modelMapper.map(request, RequestDto.class);
                    return new ResponseEntity<>(requestDto, HttpStatus.OK);
                }
            } catch (IllegalArgumentException e) {
                log.error(e.getMessage());
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                log.error(e.getMessage());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
}
