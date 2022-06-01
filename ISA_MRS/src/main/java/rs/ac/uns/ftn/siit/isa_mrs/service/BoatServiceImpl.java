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
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.BoatDtos.BoatProfileDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.BoatDtos.BoatsForMenuDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BoatDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.AddBoatDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PhotoDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.*;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;
import rs.ac.uns.ftn.siit.isa_mrs.repository.*;
import rs.ac.uns.ftn.siit.isa_mrs.security.JwtDecoder;
import rs.ac.uns.ftn.siit.isa_mrs.repository.BoatRepo;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.BoatDtos.BoatProfileDto;
import rs.ac.uns.ftn.siit.isa_mrs.repository.ClientRepo;

import java.util.ArrayList;
import java.util.Collection;
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
    private final CancellationFeeRepo cancellationFeeRepo;
    private final AdditionalServiceRepo additionalServiceRepo;
    private final ConductRuleRepo conductRuleRepo;
    private final JwtDecoder jwtDecoder;

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
        log.info("Uslo u servis");
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
        log.info(String.valueOf(result));
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

    /*@Override
    public ResponseEntity<BoatDto> addNewBoat(AddBoatDto boatDto) {
        log.info("Servis");
        Optional<RentalObjectOwner> owner = ownerRepo.findByEmail(boatDto.getBoatOwnerEmail());
        if(owner.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        else{
            try{
                RentalObjectOwner boatOwner = owner.get();
                Address address = new Address();
                address.setCountry(boatDto.getAddress().getCountry());
                address.setCity(boatDto.getAddress().getCity());
                address.setStreet(boatDto.getAddress().getStreet());
                address.setNumber(boatDto.getAddress().getNumber());
                address.setLatitude(boatDto.getAddress().getLatitude());
                address.setLongitude(boatDto.getAddress().getLongitude());
                addressRepo.save(address);

                CancellationFee cancellationFee = new CancellationFee();
                cancellationFee.setFeeType(boatDto.getCancellationFee().getFeeType());
                cancellationFee.setValue(boatDto.getCancellationFee().getValue());
                cancellationFeeRepo.save(cancellationFee);

                Boat boat = new Boat();
                boat.setName(boatDto.getName());
                boat.setRentalObjectType(RentalObjectType.Boat);
                boat.setDescription(boatDto.getDescription());
                boat.setType(boatDto.getType());
                boat.setLength(boatDto.getLength());
                boat.setEngineNumber(boatDto.getEngineNumber());
                boat.setEnginePower(boatDto.getEnginePower());
                boat.setMaxSpeed(boatDto.getMaxSpeed());
                boat.setPrice(boatDto.getPrice());
                boat.setCapacity(boatDto.getCapacity());
                boat.setRentalObjectOwner(boatOwner);
                boat.setAddress(address);
                boat.setCancellationFee(cancellationFee);

                Collection<AdditionalService> additionalServices = new ArrayList<>();
                for(var item : boatDto.getAdditionalServices()){
                    AdditionalService additionalService = new AdditionalService();
                    additionalService.setName(item.getName());
                    additionalService.setPrice(item.getPrice());
                    additionalServiceRepo.save(additionalService);
                    additionalServices.add(additionalService);
                }
                boat.setAdditionalServices(additionalServices);

                Collection<ConductRule> conductRules = new ArrayList<>();
                for(var item : boatDto.getConductRules()){
                    ConductRule conductRule = new ConductRule();
                    conductRule.setType(item.getType());
                    conductRule.setRule(item.getRule());
                    conductRuleRepo.save(conductRule);
                    conductRules.add(conductRule);
                }
                boat.setConductRules(conductRules);

                Collection<Photo> photos = new ArrayList<>();
                for(var item : boatDto.getPhotos()){
                    Photo photo = new Photo();
                    photo.setPhoto(item.getPhoto());
                    photos.add(photo);
                }
                boat.setPhotos(photos);

                Collection<FishingEquipment> fishingEquipments = new ArrayList<>();
                for(var item : boatDto.getFishingEquipment()){
                    FishingEquipment fishingEquipment = new FishingEquipment();
                    fishingEquipment.setName(item.getName());
                    fishingEquipments.add(fishingEquipment);
                }
                boat.setFishingEquipment(fishingEquipments);

                Collection<NavigationEquipment> navigationEquipments = new ArrayList<>();
                for(var item : boatDto.getNavigationEquipment()){
                    NavigationEquipment navigationEquipment = new NavigationEquipment();
                    navigationEquipment.setName(item.getName());
                    navigationEquipments.add(navigationEquipment);
                }
                boat.setNavigationEquipment(navigationEquipments);
                boatRepo.save(boat);
                BoatDto BoatDto = modelMapper.map(boat, BoatDto.class);

                return new ResponseEntity<>(BoatDto, HttpStatus.OK);
            } catch (IllegalArgumentException e){
                log.error(e.getMessage());
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                log.error(e.getMessage());
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }*/

}
