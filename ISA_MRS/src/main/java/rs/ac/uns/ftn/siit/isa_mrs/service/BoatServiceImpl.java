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
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.siit.isa_mrs.dto.AdventureDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.BoatDtos.BoatProfileDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.BoatDtos.BoatsForMenuDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BoatDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.AddBoatDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PhotoDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.*;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.ConductType;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.FeeType;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.UserType;
import rs.ac.uns.ftn.siit.isa_mrs.repository.*;
import rs.ac.uns.ftn.siit.isa_mrs.security.JwtDecoder;
import rs.ac.uns.ftn.siit.isa_mrs.repository.BoatRepo;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.BoatDtos.BoatProfileDto;
import rs.ac.uns.ftn.siit.isa_mrs.repository.ClientRepo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BoatServiceImpl implements BoatService{

    private final BoatRepo boatRepo;
    private final ModelMapper modelMapper;
    private final RentalObjectServiceImpl rentalService;
    private final ClientRepo clientRepo;
    private final RentalObjectOwnerRepo ownerRepo;
    private final AddressRepo addressRepo;
    private final AdditionalServiceRepo additionalServiceRepo;
    private final ConductRuleRepo conductRuleRepo;
    private final FishingEquipmentRepo fishingEquipmentRepo;
    private final NavigationEquipmentRepo navigationEquipmentRepo;
    private final JwtDecoder jwtDecoder;
    private final PhotoRepo photoRepo;

    @Override
    public ResponseEntity<BoatProfileDto> getBoat(Long id, int page, int pageSize, String token) {
        try{
            JwtDecoder.DecodedToken decodedToken;
            try {
                decodedToken = jwtDecoder.decodeToken(token);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            Optional<Boat> rental = boatRepo.findById(id);
            if (rental.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            BoatProfileDto boatDto = modelMapper.map(rental, BoatProfileDto.class);
            Boat boat = rental.get();
            boatDto.setReviews(rentalService.getRentalReviews(boat, page, pageSize));
            boatDto.setGrade(rentalService.calculateRentalRating(boat));
            boatDto.setOwnerGrade(rentalService.calculateOwnerRating(boat.getRentalObjectOwner()));
            Optional<Client> optionalClient = clientRepo.findByEmail(decodedToken.getEmail());
            if(optionalClient.isPresent()){
                Client client = optionalClient.get();
                if(boat.getSubscribers().contains(client)) boatDto.setIsUserSubscribed(true);
            }
            return new ResponseEntity<>(boatDto, HttpStatus.OK);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<PageDto<BoatsForMenuDto>> findBoatsWithPaginationSortedByField(int offset, int pageSize,
                                                                                         String field) {
        PageDto<BoatsForMenuDto> result = new PageDto<>();
        try{
            Pageable pageable = PageRequest.of(offset, pageSize).withSort(Sort.by(field));
            Page<Boat> boatsPage = boatRepo.findByRentalObjectType(RentalObjectType.Boat, pageable);
            Collection<BoatsForMenuDto> boatDtos = new ArrayList<>();
            boatsPage.getContent().forEach(boat -> boatDtos.add(setUpMenuDto(boat)));
            result.setContent(boatDtos);
            result.setPages(boatsPage.getTotalPages());
            result.setCurrentPage(boatsPage.getNumber() + 1);
            result.setPageSize(boatsPage.getSize());
            if (boatsPage.getContent().isEmpty()) {
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
    public ResponseEntity<PageDto<BoatDto>> findBoatsWithPaginationSortedByFieldAndFilteredByOwner(int offset, int pageSize, String field, String token) {
        JwtDecoder.DecodedToken decodedToken;
        try {
            decodedToken = jwtDecoder.decodeToken(token);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try{
            Pageable pageable = PageRequest.of(offset, pageSize).withSort(Sort.by(field));
            Page<Boat> boatsPage = boatRepo.findAllByRentalObjectTypeAndRentalObjectOwnerEmail(RentalObjectType.Boat, decodedToken.getEmail(), pageable);
            return new ResponseEntity<>(packBoats(boatsPage), HttpStatus.OK);
        } catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<BoatDto> findBoat(Long id) {
        try {
            Boat boat = boatRepo.getById(id);
            return new ResponseEntity<>(mapBoatsToDto(boat), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private PageDto<BoatDto> packBoats(Page<Boat> boatsPage) {
        PageDto<BoatDto> result = new PageDto<>();
        Collection<BoatDto> boatDtos = new ArrayList<>();
        boatsPage.getContent().forEach(rentalObject -> boatDtos.add(mapBoatsToDto(rentalObject)));
        result.setContent(boatDtos);
        result.setPages(boatsPage.getTotalPages());
        result.setCurrentPage(boatsPage.getNumber());
        result.setPageSize(boatsPage.getSize());
        return result;
    }

    private BoatDto mapBoatsToDto(Boat boat) {
        BoatDto boatDto = modelMapper.map(boat, BoatDto.class);
        boatDto.setIsDeletable(boat.getReservations().size() == 0);
        return boatDto;
    }

    private @NotNull BoatsForMenuDto setUpMenuDto(Boat boat) {
        BoatsForMenuDto boatDto = modelMapper.map(boat, BoatsForMenuDto.class);
        boatDto.setGrade(rentalService.calculateRentalRating(boat));
        if(boat.getPhotos().size() != 0) {
            Optional<Photo> photo = boat.getPhotos().stream().findFirst();
            photo.ifPresent(value -> boatDto.setDisplayPhoto(modelMapper.map(value, PhotoDto.class)));
        }
        return boatDto;
    }

    @Override
    public ResponseEntity<BoatDto> addBoatPhotos(Long id, List<String> photos) {
        try {
            Optional<Boat> boat = boatRepo.findById(id);
            if(boat.isEmpty()) {
                throw new Exception();
            }
            List<Photo> photoList = new ArrayList<>();
            photos.forEach(photo -> {
                Photo photo1 = new Photo();
                photo1.setPhoto(photo);
                photo1.setRentalObject(boat.get());
                photoRepo.save(photo1);
                photoList.add(photo1);
            });
            Boat boat1 = boat.get();
            boat1.setPhotos(photoList);
            return new ResponseEntity<>(modelMapper.map(boat1, BoatDto.class), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<BoatDto> addBoat(AddBoatDto boatDto, String token) {
        try {
            JwtDecoder.DecodedToken decodedToken;
            try {
                decodedToken = jwtDecoder.decodeToken(token);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            Optional<RentalObjectOwner> owner = ownerRepo.findByEmail(decodedToken.getEmail());
            if (owner.isEmpty() || !owner.get().getUserType().equals(UserType.BoatOwner)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            Boat newBoat = boatDtoToBoat(boatDto, owner.get());
            BoatDto result = modelMapper.map(newBoat, BoatDto.class);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Boat boatDtoToBoat(AddBoatDto boatDto, RentalObjectOwner owner) {
        Boat boat = new Boat();
        List<AdditionalService> additionalServices = new ArrayList<>();
        List<ConductRule> conductRules = new ArrayList<>();
        List<FishingEquipment> fishingEquipments = new ArrayList<>();
        List<NavigationEquipment> navigationEquipments = new ArrayList<>();
        boatDto.getAdditionalServices().forEach(service -> {
            AdditionalService additionalService = modelMapper.map(service, AdditionalService.class);
            additionalServiceRepo.save(additionalService);
            additionalServices.add(additionalService);
        });
        additionalServiceRepo.saveAll(additionalServices);
        boatDto.getConductRules().forEach(rule -> {
            ConductRule conductRule = new ConductRule();
            conductRule.setRule(rule.getRule());
            conductRule.setType(ConductType.valueOf(rule.getType()));
            conductRuleRepo.save(conductRule);
            conductRules.add(conductRule);
        });
        boatDto.getFishingEquipment().forEach(equipment -> {
            FishingEquipment fishingEquipment = modelMapper.map(equipment, FishingEquipment.class);
            fishingEquipmentRepo.save(fishingEquipment);
            fishingEquipments.add(fishingEquipment);
        });
        boatDto.getNavigationEquipment().forEach(equipment1 -> {
            NavigationEquipment navigationEquipment = modelMapper.map(equipment1, NavigationEquipment.class);
            navigationEquipmentRepo.save(navigationEquipment);
            navigationEquipments.add(navigationEquipment);
        });
        CancellationFee cancellationFee = new CancellationFee();
        cancellationFee.setValue(boatDto.getCancellationFee().getValue());
        if (cancellationFee.getValue() == 0 ) {
            cancellationFee.setFeeType(FeeType.Free);
        } else {
            cancellationFee.setFeeType(FeeType.Percentile);
        }
        cancellationFeeRepo.save(cancellationFee);
        Address address = modelMapper.map(boatDto.getAddress(), Address.class);
        addressRepo.save(address);
        boat.setName(boatDto.getName());
        boat.setCancellationFee(cancellationFee);
        boat.setAddress(address);
        boat.setAdditionalServices(additionalServices);
        boat.setFishingEquipment(fishingEquipments);
        boat.setNavigationEquipment(navigationEquipments);
        boat.setConductRules(conductRules);
        boat.setRentalObjectOwner(owner);
        boat.setRentalObjectType(RentalObjectType.Boat);
        boat.setDescription(boatDto.getDescription());
        boat.setCapacity(boatDto.getCapacity());
        boat.setPrice(boatDto.getPrice());
        boat.setType(boatDto.getType());
        boat.setLength(boatDto.getLength());
        boat.setEnginePower(boatDto.getEnginePower());
        boat.setEngineNumber(boatDto.getEngineNumber());
        boat.setMaxSpeed(boatDto.getMaxSpeed());
        boatRepo.save(boat);

        fishingEquipments.forEach(equipment -> {
            equipment.setBoat(boat);
            fishingEquipmentRepo.save(equipment);
        });
        navigationEquipments.forEach(equipment1 -> {
            equipment1.setBoat(boat);
            navigationEquipmentRepo.save(equipment1);
        });
        additionalServices.forEach(service -> {
            service.setRentalObject(boat);
            additionalServiceRepo.save(service);
        });
        conductRules.forEach(rule -> {
            rule.setRentalObject(boat);
            conductRuleRepo.save(rule);
        });
        cancellationFee.setRentalObject(boat);
        cancellationFeeRepo.save(cancellationFee);
        return boat;
    }
}
