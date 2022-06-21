package rs.ac.uns.ftn.siit.isa_mrs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.SpecialOfferDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.VacationRentalDtos.VacationRentalProfileDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.VacationRentalDtos.VacationRentalsForMenuDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.AddVacationRentalDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PhotoDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.VacationRentalDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.*;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;
import rs.ac.uns.ftn.siit.isa_mrs.repository.*;
import rs.ac.uns.ftn.siit.isa_mrs.security.JwtDecoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class VacationRentalServiceImpl implements VacationRentalService{

    private final VacationRentalRepo vacationRentalRepo;
    private final ModelMapper modelMapper;
    private final RentalObjectOwnerRepo ownerRepo;
    private final AddressRepo addressRepo;
    private final AdditionalServiceRepo additionalServiceRepo;
    private final ConductRuleRepo conductRuleRepo;
    private final RoomRepo roomRepo;
    private final ClientRepo clientRepo;
    private final ClientServiceImpl clientService;
    private final RentalObjectServiceImpl rentalService;
    private final JwtDecoder jwtDecoder;

    @Override
    public ResponseEntity<VacationRentalProfileDto> getVacationRental(Long id, int page, int pageSize, String token) {
        try{
            JwtDecoder.DecodedToken decodedToken;
            try {
                decodedToken = jwtDecoder.decodeToken(token);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            Optional<VacationRental> rental = vacationRentalRepo.findById(id);
            if(rental.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            VacationRentalProfileDto rentalDto = modelMapper.map(rental, VacationRentalProfileDto.class);
            VacationRental vacationRental = rental.get();
            rentalDto.setReviews(rentalService.getRentalReviews(vacationRental, page, pageSize));
            rentalDto.setGrade(rentalService.calculateRentalRating(vacationRental));
            rentalDto.setOwnerGrade(rentalService.calculateOwnerRating(vacationRental.getRentalObjectOwner()));
            rentalDto.setSpecialOffers(rentalService.getFutureSpecialOffers(vacationRental.getSpecialOffers()));
            Optional<Client> optionalClient = clientRepo.findByEmail(decodedToken.getEmail());
            if(optionalClient.isPresent()){
                Client client = optionalClient.get();
                if(vacationRental.getSubscribers().contains(client)) rentalDto.setIsUserSubscribed(true);
                rentalDto.setPenalties((clientService.setUpPenalties(client)).size());
            }
            return new ResponseEntity<>(rentalDto, HttpStatus.OK);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<PageDto<VacationRentalDto>> findVacationRentalWithPaginationSortedByField(int offset, int pageSize, String field) {
        try{
            Pageable pageable = PageRequest.of(offset, pageSize).withSort(Sort.by(field));
            Page<VacationRental> vacationRentalsPage = vacationRentalRepo.findAllByRentalObjectType(RentalObjectType.VacationRental, pageable);
            return new ResponseEntity<>(packVacationRentals(vacationRentalsPage), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<PageDto<VacationRentalDto>> findVacationRentalsWithPaginationSortedByFieldAndFilteredByOwner(int offset, int pageSize, String field, String token) {
        JwtDecoder.DecodedToken decodedToken;
        try {
            decodedToken = jwtDecoder.decodeToken(token);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try{
            Pageable pageable = PageRequest.of(offset, pageSize).withSort(Sort.by(field));
            Page<VacationRental> vacationRentalsPage = vacationRentalRepo.findAllByRentalObjectTypeAndRentalObjectOwnerEmail(RentalObjectType.VacationRental, decodedToken.getEmail(), pageable);
            return new ResponseEntity<>(packVacationRentals(vacationRentalsPage), HttpStatus.OK);
        } catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<VacationRentalDto> findVacationRental(Long id) {
        try {
            VacationRental vacationRental = vacationRentalRepo.getById(id);
            return new ResponseEntity<>(mapVacationRentalsToDto(vacationRental), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private PageDto<VacationRentalDto> packVacationRentals(Page<VacationRental> vacationRentalsPage) {
        PageDto<VacationRentalDto> result = new PageDto<>();
        Collection<VacationRentalDto> vacationRentalDtos = new ArrayList<>();
        vacationRentalsPage.getContent().forEach(rentalObject -> vacationRentalDtos.add(mapVacationRentalsToDto(rentalObject)));
        result.setContent(vacationRentalDtos);
        result.setPages(vacationRentalsPage.getTotalPages());
        result.setCurrentPage(vacationRentalsPage.getNumber());
        result.setPageSize(vacationRentalsPage.getSize());
        return result;
    }

    private VacationRentalDto mapVacationRentalsToDto(VacationRental vacationRental) {
        VacationRentalDto vacationRentalDto = modelMapper.map(vacationRental, VacationRentalDto.class);
        vacationRentalDto.setIsDeletable(vacationRental.getReservations().size() == 0);
        return vacationRentalDto;
    }

    @Override
    public ResponseEntity<PageDto<VacationRentalsForMenuDto>> findVacationRentalsWithPaginationSortedByField(
            int offset, int pageSize, String field) {
        PageDto<VacationRentalsForMenuDto> result = new PageDto<>();
        try{
            Pageable pageable = PageRequest.of(offset, pageSize).withSort(Sort.by(field));
            Page<VacationRental> vacationRentalsPage = vacationRentalRepo
                    .findAllByRentalObjectType(RentalObjectType.VacationRental, pageable);
            Collection<VacationRentalsForMenuDto> vacationRentalDtos = new ArrayList<>();
            vacationRentalsPage.getContent().forEach(vacationRental ->
                    vacationRentalDtos.add(setUpMenuDto(vacationRental)));
            result.setContent(vacationRentalDtos);
            result.setPages(vacationRentalsPage.getTotalPages());
            result.setCurrentPage(vacationRentalsPage.getNumber() + 1);
            result.setPageSize(vacationRentalsPage.getSize());
            if (vacationRentalsPage.getContent().isEmpty()) {
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
    public long countVacationRentals() {
        return vacationRentalRepo.findAllByIsActive(true).size();
    }

//    @Override
//    public ResponseEntity<VacationRentalDto> addNewVacationRental(AddVacationRentalDto vrd) {
//        log.info("Uslo u kontroler");
//        log.info(vrd.getName());
//        log.info(vrd.getOwnerEmail());
//        Optional<RentalObjectOwner> owner = ownerRepo.findByEmail(vrd.getOwnerEmail());
//        log.info(String.valueOf(owner));
//        if (owner.isEmpty()){
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//        else{
//            try{
//                RentalObjectOwner vacationRentalOwner = owner.get();
//                Address address = new Address();
//                address.setCountry(vrd.getAddress().getCountry());
//                address.setCity(vrd.getAddress().getCity());
//                address.setStreet(vrd.getAddress().getStreet());
//                address.setNumber(vrd.getAddress().getNumber());
//                address.setLatitude(vrd.getAddress().getLatitude());
//                address.setLongitude(vrd.getAddress().getLongitude());
//                addressRepo.save(address);
//                log.info("Adresa napravljena");
//
//                CancellationFee cancellationFee = new CancellationFee();
//                cancellationFee.setFeeType(vrd.getCancellationFee().getFeeType());
//                cancellationFee.setValue(vrd.getCancellationFee().getValue());
//                cancellationFeeRepo.save(cancellationFee);
//
//                VacationRental vacationRental = new VacationRental();
//                vacationRental.setName(vrd.getName());
//                vacationRental.setRentalObjectType(RentalObjectType.VacationRental);
//                vacationRental.setDescription(vrd.getDescription());
//                vacationRental.setPrice(vrd.getPrice());
//                vacationRental.setCapacity(vrd.getCapacity());
//                vacationRental.setRentalObjectOwner(vacationRentalOwner);
//                vacationRental.setAddress(address);
//                vacationRental.setCancellationFee(cancellationFee);
//
//                Collection<AdditionalService> additionalServices = new ArrayList<>();
//                for(var item : vrd.getAdditionalServices()){
//                    AdditionalService additionalService = new AdditionalService();
//                    additionalService.setName(item.getName());
//                    additionalService.setPrice(item.getPrice());
//                    additionalServiceRepo.save(additionalService);
//                    additionalServices.add(additionalService);
//                }
//                log.info("Dodatne usluge napravljene");
//                vacationRental.setAdditionalServices(additionalServices);
//
//                Collection<ConductRule> conductRules = new ArrayList<>();
//                for(var item : vrd.getConductRules()){
//                    ConductRule conductRule = new ConductRule();
//                    conductRule.setType(item.getType());
//                    conductRule.setRule(item.getRule());
//                    conductRuleRepo.save(conductRule);
//                    conductRules.add(conductRule);
//                }
//                vacationRental.setConductRules(conductRules);
//
//                Collection<Room> rooms = new ArrayList<>();
//                for(var item : vrd.getRooms()){
//                    Room room = new Room();
//                    room.setBeds(item.getBeds());
//                    roomRepo.save(room);
//                    rooms.add(room);
//                }
//                vacationRental.setRooms(rooms);
//                log.info("Dodate sobe");
//                Collection<Photo> photos = new ArrayList<>();
//                for(var item : vrd.getPhotos()){
//                    Photo photo = new Photo();
//                    photo.setPhoto(item.getPhoto());
//                    photos.add(photo);
//                }
//                vacationRental.setPhotos(photos);
//                vacationRentalRepo.save(vacationRental);
//                VacationRentalDto RentalDto = modelMapper.map(vacationRental, VacationRentalDto.class);
//
//                return new ResponseEntity<>(RentalDto, HttpStatus.OK);
//            } catch (IllegalArgumentException e) {
//                log.error(e.getMessage());
//                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//            } catch (Exception e) {
//                log.error(e.getMessage());
//                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//        }
//    }

    private @NotNull VacationRentalsForMenuDto setUpMenuDto(VacationRental rental) {
        VacationRentalsForMenuDto rentalDto = modelMapper.map(rental, VacationRentalsForMenuDto.class);
        rentalDto.setGrade(rentalService.calculateRentalRating(rental));
        if(rental.getPhotos().size() != 0) {
            Optional<Photo> photo = rental.getPhotos().stream().findFirst();
            photo.ifPresent(value -> rentalDto.setDisplayPhoto(modelMapper.map(value, PhotoDto.class)));
        }
        return rentalDto;
    }
}
