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
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.VacationRentalDtos.VacationRentalProfileDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.VacationRentalDtos.VacationRentalsForMenuDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.AddVacationRentalDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PhotoDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.VacationRentalDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.*;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.ConductType;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.UserType;
import rs.ac.uns.ftn.siit.isa_mrs.repository.*;
import rs.ac.uns.ftn.siit.isa_mrs.security.JwtDecoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
    private final RentalObjectServiceImpl rentalService;
    private final JwtDecoder jwtDecoder;
    private final PhotoRepo photoRepo;

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
            Optional<Client> optionalClient = clientRepo.findByEmail(decodedToken.getEmail());
            if(optionalClient.isPresent()){
                Client client = optionalClient.get();
                if(vacationRental.getSubscribers().contains(client)) rentalDto.setIsUserSubscribed(true);
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
    public ResponseEntity<VacationRentalDto> addVacationRental(AddVacationRentalDto addVacationRentalDto, String token) {
        try {
            JwtDecoder.DecodedToken decodedToken;
            try {
                decodedToken = jwtDecoder.decodeToken(token);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            Optional<RentalObjectOwner> owner = ownerRepo.findByEmail(decodedToken.getEmail());
            if (owner.isEmpty() || !owner.get().getUserType().equals(UserType.VacationRentalOwner)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            VacationRental newVacationRental = vacationRentalDtoToVacationRental(addVacationRentalDto, owner.get());
            VacationRentalDto result = modelMapper.map(newVacationRental, VacationRentalDto.class);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<VacationRentalDto> addVacationRentalPhotos(Long id, List<String> photos) {
        try {
            Optional<VacationRental> vacationRental = vacationRentalRepo.findById(id);
            if(vacationRental.isEmpty()){
                throw new Exception();
            }
            List<Photo> photoList = new ArrayList<>();
            photos.forEach(photo -> {
                Photo photo1 = new Photo();
                photo1.setPhoto(photo);
                photo1.setRentalObject(vacationRental.get());
                photoRepo.save(photo1);
                photoList.add(photo1);
            });
            VacationRental vacationRental1 = vacationRental.get();
            vacationRental1.setPhotos(photoList);
            return new ResponseEntity<>(modelMapper.map(vacationRental1, VacationRentalDto.class), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private VacationRental vacationRentalDtoToVacationRental(AddVacationRentalDto vacationRentalDto, RentalObjectOwner owner) {
        VacationRental vacationRental = new VacationRental();
        List<AdditionalService> additionalServices = new ArrayList<>();
        List<ConductRule> conductRules = new ArrayList<>();
        List<Room> rooms = new ArrayList<>();
        vacationRentalDto.getAdditionalServices().forEach(service -> {
            AdditionalService additionalService = modelMapper.map(service, AdditionalService.class);
            additionalServiceRepo.save(additionalService);
            additionalServices.add(additionalService);
        });
        additionalServiceRepo.saveAll(additionalServices);
        vacationRentalDto.getConductRules().forEach(rule -> {
            ConductRule conductRule = new ConductRule();
            conductRule.setRule(rule.getRule());
            conductRule.setType(ConductType.valueOf(rule.getType()));
            conductRuleRepo.save(conductRule);
            conductRules.add(conductRule);
        });
        vacationRentalDto.getRooms().forEach(room -> {
            Room vacationRentalRoom = modelMapper.map(room, Room.class);
            roomRepo.save(vacationRentalRoom);
            rooms.add(vacationRentalRoom);
        });
        Address address = modelMapper.map(vacationRentalDto.getAddress(), Address.class);
        addressRepo.save(address);
        vacationRental.setName(vacationRentalDto.getName());
        vacationRental.setCancellationFee(vacationRentalDto.getCancellationFee());
        vacationRental.setAddress(address);
        vacationRental.setAdditionalServices(additionalServices);
        vacationRental.setRooms(rooms);
        vacationRental.setConductRules(conductRules);
        vacationRental.setRentalObjectOwner(owner);
        vacationRental.setRentalObjectType(RentalObjectType.VacationRental);
        vacationRental.setDescription(vacationRentalDto.getDescription());
        vacationRental.setCapacity(vacationRentalDto.getCapacity());
        vacationRental.setPrice(vacationRentalDto.getPrice());
        vacationRentalRepo.save(vacationRental);

        rooms.forEach(room -> {
            room.setVacationRental(vacationRental);
            roomRepo.save(room);
        });
        additionalServices.forEach(service -> {
            service.setRentalObject(vacationRental);
            additionalServiceRepo.save(service);
        });
        conductRules.forEach(rule -> {
            rule.setRentalObject(vacationRental);
            conductRuleRepo.save(rule);
        });
        return vacationRental;
    }

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
