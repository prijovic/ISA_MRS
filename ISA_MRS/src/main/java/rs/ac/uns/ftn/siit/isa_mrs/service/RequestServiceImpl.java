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
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.SignUpDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.*;
import rs.ac.uns.ftn.siit.isa_mrs.dto.RequestDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.RespondedRequestDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RequestStatus;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RequestType;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.UserType;
import rs.ac.uns.ftn.siit.isa_mrs.repository.*;

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
    private final AddressRepo addressRepo;
    private final RentalObjectOwnerRepo rentalOwnerRepo;
    private final ClientRepo clientRepo;


    @Override
    public ResponseEntity<PageDto<RequestDto>> findRequestsWithPaginationSortedByField(int offset, int pageSize, String types, String field) {
        PageDto<RequestDto> result = new PageDto<>();
        try{
            Pageable pageable = PageRequest.of(offset, pageSize).withSort(Sort.by(field));
            log.info(requestRepo.findAll().toString());
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
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<RespondedRequestDto> changeRequestStatus(Long id, String status, String reason, String adminEmail) {
        try {
            Request request = requestRepo.getById(id);
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
            if (request.getType().equals(RequestType.AccountDeletion)) {
                user.setActive(false);
            }
            else if (request.getType().equals(RequestType.SignUp)){
                user.setActive(true);
            }
            userRepo.save(user);
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
                request.setStatus(RequestStatus.Pending);
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
                log.error(e.getMessage());
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                log.error(e.getMessage());
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

    @Override
    public ResponseEntity<RequestDto> createSignUpRequest(SignUpDto sud) {
        Optional<User> user = userRepo.findByEmail(sud.getEmail());
        if (user.isPresent()){
            return new ResponseEntity<>(null, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        else {
            try {
                Address address = new Address();
                address.setCountry(sud.getAddress().getCountry());
                address.setCity(sud.getAddress().getCity());
                address.setStreet(sud.getAddress().getStreet());
                address.setNumber(sud.getAddress().getNumber());
                address.setLatitude(sud.getAddress().getLatitude());
                address.setLongitude(sud.getAddress().getLongitude());
                addressRepo.save(address);

                if(sud.getUserType() == UserType.Client) {
                    Client client = new Client();
                    client.setUserType(sud.getUserType());
                    client.setName(sud.getName());
                    client.setSurname(sud.getSurname());
                    client.setEmail(sud.getEmail());
                    client.setPhone(sud.getPhoneNumber());
                    client.setPhoto(sud.getPhoto());
                    client.setActive(false);
                    client.setAddress(address);
                    client.setPoints(0);
                    clientRepo.save(client);
                    // send email
                    return new ResponseEntity<>(null, HttpStatus.OK);
                }

                Request request = new Request();
                request.setStatus(RequestStatus.Pending);
                request.setReason(sud.getReason());
                request.setType(RequestType.SignUp);
                request.setTimeStamp(LocalDateTime.now());

                if(sud.getUserType() == UserType.Admin) {
                    Admin admin = new Admin();
                    admin.setUserType(sud.getUserType());
                    admin.setName(sud.getName());
                    admin.setSurname(sud.getSurname());
                    admin.setEmail(sud.getEmail());
                    admin.setPhone(sud.getPhoneNumber());
                    admin.setPhoto(sud.getPhoto());
                    admin.setActive(false);
                    admin.setAddress(address);
                    adminRepo.save(admin);
                    request.setUser(admin);
                    requestRepo.save(request);
                    admin.setRequest(request);
                    // send email
                }
                else {
                    RentalObjectOwner owner = new RentalObjectOwner();
                    owner.setUserType(sud.getUserType());
                    owner.setName(sud.getName());
                    owner.setSurname(sud.getSurname());
                    owner.setEmail(sud.getEmail());
                    owner.setPhone(sud.getPhoneNumber());
                    owner.setPhoto(sud.getPhoto());
                    owner.setActive(false);
                    owner.setAddress(address);
                    owner.setPoints(0);
                    rentalOwnerRepo.save(owner);
                    request.setUser(owner);
                    requestRepo.save(request);
                    owner.setRequest(request);
                }

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
