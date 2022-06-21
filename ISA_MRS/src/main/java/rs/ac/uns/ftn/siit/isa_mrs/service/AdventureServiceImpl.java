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
import rs.ac.uns.ftn.siit.isa_mrs.dto.*;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.AdventureDtos.AdventureProfileDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.AdventureDtos.AdventuresForMenuDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.*;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.ConductType;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.UserType;
import rs.ac.uns.ftn.siit.isa_mrs.repository.*;
import rs.ac.uns.ftn.siit.isa_mrs.security.JwtDecoder;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AdventureServiceImpl implements AdventureService{
    private final RentalObjectOwnerRepo rentalObjectOwnerRepo;
    private final AdditionalServiceRepo additionalServiceRepo;
    private final AdventureEquipmentRepo adventureEquipmentRepo;
    private final ConductRuleRepo conductRuleRepo;
    private final RentalObjectService rentalService;
    private final ClientServiceImpl clientService;
    private final AdventureRepo adventureRepo;
    private final AddressRepo addressRepo;
    private final ModelMapper modelMapper;
    private final ClientRepo clientRepo;
    private final JwtDecoder jwtDecoder;
    private final PhotoRepo photoRepo;

    @Override
    public ResponseEntity<AdventureProfileDto> getAdventure(Long id, int page, int pageSize, String token) {
        try{
            JwtDecoder.DecodedToken decodedToken;
            try {
                decodedToken = jwtDecoder.decodeToken(token);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            Optional<Adventure> rental = adventureRepo.findById(id);
            if (rental.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            AdventureProfileDto adventureDto = modelMapper.map(rental, AdventureProfileDto.class);
            Adventure adventure = rental.get();
            adventureDto.setReviews(rentalService.getRentalReviews(adventure, page, pageSize));
            adventureDto.setGrade(rentalService.calculateRentalRating(adventure));
            adventureDto.setOwnerGrade(rentalService.calculateOwnerRating(adventure.getRentalObjectOwner()));
            adventureDto.setIsDeletable(isAdventureDeletable(adventure));
            Optional<Client> optionalClient = clientRepo.findByEmail(decodedToken.getEmail());
            if(optionalClient.isPresent()){
                Client client = optionalClient.get();
                if(adventure.getSubscribers().contains(client)) adventureDto.setIsUserSubscribed(true);
                adventureDto.setPenalties((clientService.setUpPenalties(client)).size());
            }
            return new ResponseEntity<>(adventureDto, HttpStatus.OK);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<PageDto<AdventuresForMenuDto>> findAdventuresWithPaginationSortedByField(int offset, int pageSize, String field) {
        try{
            PageDto<AdventuresForMenuDto> result = new PageDto<>();
            Pageable pageable = PageRequest.of(offset, pageSize).withSort(Sort.by(field));
            Page<Adventure> adventuresPage = adventureRepo.findByRentalObjectType(RentalObjectType.Adventure, pageable);
            Collection<AdventuresForMenuDto> adventureDtos = new ArrayList<>();
            adventuresPage.getContent().forEach(adventure ->
                    adventureDtos.add(setUpMenuDto(adventure)));
            result.setContent(adventureDtos);
            result.setPages(adventuresPage.getTotalPages());
            result.setCurrentPage(adventuresPage.getNumber() + 1);
            result.setPageSize(adventuresPage.getSize());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<PageDto<AdventureDto>> findAdventuresWithPaginationSortedByFieldAndFilteredByOwner(int offset, int pageSize, String field, String token) {
        JwtDecoder.DecodedToken decodedToken;
        try {
            decodedToken = jwtDecoder.decodeToken(token);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try{
            Pageable pageable = PageRequest.of(offset, pageSize).withSort(Sort.by(field));
            Page<Adventure> adventuresPage = adventureRepo.findAllByRentalObjectTypeAndRentalObjectOwnerEmail(RentalObjectType.Adventure, decodedToken.getEmail(), pageable);
            return new ResponseEntity<>(packAdventures(adventuresPage), HttpStatus.OK);
        } catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<AdventureDto> findAdventure(Long id) {
        try {
            Adventure adventure = adventureRepo.getById(id);
            return new ResponseEntity<>(mapAdventuresToDto(adventure), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<AdventureDto> addAdventure(rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.AdventureDto adventureDto, String token) {
        try {
            JwtDecoder.DecodedToken decodedToken;
            try {
                decodedToken = jwtDecoder.decodeToken(token);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            Optional<RentalObjectOwner> owner = rentalObjectOwnerRepo.findByEmail(decodedToken.getEmail());
            if (owner.isEmpty() || !owner.get().getUserType().equals(UserType.Instructor)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            Adventure newAdventure = adventureDtoToAdventure(adventureDto, owner.get());
            AdventureDto result = modelMapper.map(newAdventure, AdventureDto.class);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<AdventureDto> addAdventurePhotos(Long id, List<String> photos) {
        try {
            Optional<Adventure> adventure = adventureRepo.findById(id);
            if (adventure.isEmpty()) {
                throw new Exception();
            }
            List<Photo> photoList = new ArrayList<>();
            photos.forEach(photo -> {
                Photo photo1 = new Photo();
                photo1.setPhoto(photo);
                photo1.setRentalObject(adventure.get());
                photoRepo.save(photo1);
                photoList.add(photo1);
            });
            Adventure adventure1 = adventure.get();
            adventure1.setPhotos(photoList);
            return new ResponseEntity<>(modelMapper.map(adventure1, AdventureDto.class), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Long> updateAdventure(rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.AdventureDto adventureDto) {
        try {
            Adventure adventure = adventureRepo.getById(adventureDto.getId());
            alterAdventure(adventure, adventureDto);
            return new ResponseEntity<>(adventure.getId(), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public long countAdventure() {
        return (long) adventureRepo.findAllByIsActive(true).size();
    }

    private void alterAdventure(Adventure adventure, rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.AdventureDto adventureDto) {
        adventureDto.getAdditionalServices().forEach(service -> {
            if (adventure.hasService(service.getName(), service.getPrice())) {

            } else if (adventure.hasService(service.getName())) {
                adventure.setAdditionalServicePrice(service.getName(), service.getPrice());
            } else {
                AdditionalService additionalService = modelMapper.map(service, AdditionalService.class);
                additionalService.setRentalObject(adventure);
                additionalServiceRepo.save(additionalService);
                adventure.getAdditionalServices().add(additionalService);
            }
        });
        adventureDto.getConductRules().forEach(rule -> {
            if (!adventure.hasRule(rule.getRule(), ConductType.valueOf(rule.getType()))) {
                ConductRule conductRule = new ConductRule();
                conductRule.setRule(rule.getRule());
                conductRule.setType(ConductType.valueOf(rule.getType()));
                conductRule.setRentalObject(adventure);
                conductRuleRepo.save(conductRule);
                adventure.getConductRules().add(conductRule);
            }
        });
        adventureDto.getAdventureEquipment().forEach(equipment -> {
            if (!adventure.hasEquipment(equipment.getName())) {
                AdventureEquipment adventureEquipment = modelMapper.map(equipment, AdventureEquipment.class);
                adventureEquipment.setAdventure(adventure);
                adventureEquipmentRepo.save(adventureEquipment);
                adventure.getAdventureEquipment().add(adventureEquipment);
            }
        });
        adventure.setInitDate(adventureDto.getInitDate());
        adventure.setTermDate(adventureDto.getTermDate());
        adventure.setDuration(adventureDto.getDuration());
        adventure.setName(adventureDto.getName());
        adventure.setCancellationFee(adventureDto.getCancellationFee());
        adventure.setDescription(adventureDto.getDescription());
        adventure.setCapacity(adventureDto.getCapacity());
        adventure.setPrice(adventureDto.getPrice());
        adventureRepo.save(adventure);
    }

    private Adventure adventureDtoToAdventure(rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.AdventureDto adventureDto, RentalObjectOwner owner) {
        Adventure adventure = new Adventure();
        List<AdditionalService> additionalServices = new ArrayList<>();
        List<ConductRule> conductRules = new ArrayList<>();
        List<AdventureEquipment> adventureEquipments = new ArrayList<>();
        adventureDto.getAdditionalServices().forEach(service -> {
            AdditionalService additionalService = modelMapper.map(service, AdditionalService.class);
            additionalServiceRepo.save(additionalService);
            additionalServices.add(additionalService);
        });
        additionalServiceRepo.saveAll(additionalServices);
        adventureDto.getConductRules().forEach(rule -> {
            ConductRule conductRule = new ConductRule();
            conductRule.setRule(rule.getRule());
            conductRule.setType(ConductType.valueOf(rule.getType()));
            conductRuleRepo.save(conductRule);
            conductRules.add(conductRule);
        });
        adventureDto.getAdventureEquipment().forEach(equipment -> {
            AdventureEquipment adventureEquipment = modelMapper.map(equipment, AdventureEquipment.class);
            adventureEquipmentRepo.save(adventureEquipment);
            adventureEquipments.add(adventureEquipment);
        });
        Address address = modelMapper.map(adventureDto.getAddress(), Address.class);
        addressRepo.save(address);
        adventure.setInitDate(adventureDto.getInitDate());
        adventure.setTermDate(adventureDto.getTermDate());
        adventure.setDuration(adventureDto.getDuration());
        adventure.setName(adventureDto.getName());
        adventure.setCancellationFee(adventureDto.getCancellationFee());
        adventure.setAddress(address);
        adventure.setAdditionalServices(additionalServices);
        adventure.setAdventureEquipment(adventureEquipments);
        adventure.setConductRules(conductRules);
        adventure.setRentalObjectOwner(owner);
        adventure.setRentalObjectType(RentalObjectType.Adventure);
        adventure.setDescription(adventureDto.getDescription());
        adventure.setCapacity(adventureDto.getCapacity());
        adventure.setPrice(adventureDto.getPrice());
        adventureEquipments.forEach(equipment -> {
            equipment.setAdventure(adventure);
            adventureEquipmentRepo.save(equipment);
        });
        additionalServices.forEach(service -> {
            service.setRentalObject(adventure);
            additionalServiceRepo.save(service);
        });
        conductRules.forEach(rule -> {
            rule.setRentalObject(adventure);
            conductRuleRepo.save(rule);
        });
        adventureRepo.save(adventure);
        return adventure;
    }

    public @NotNull AdventuresForMenuDto setUpMenuDto(Adventure adventure) {
        AdventuresForMenuDto adventureDto = modelMapper.map(adventure, AdventuresForMenuDto.class);
        adventureDto.setGrade(rentalService.calculateRentalRating(adventure));
        if (adventure.getPhotos().size() != 0) {
            Optional<Photo> photo = adventure.getPhotos().stream().findFirst();
            photo.ifPresent(value -> adventureDto.setDisplayPhoto(modelMapper.map(value, PhotoDto.class)));
        }
        return adventureDto;
    }

    private PageDto<AdventureDto> packAdventures(Page<Adventure> adventuresPage) {
        PageDto<AdventureDto> result = new PageDto<>();
        Collection<AdventureDto> adventureDtos = new ArrayList<>();
        adventuresPage.getContent().forEach(rentalObject -> adventureDtos.add(mapAdventuresToDto(rentalObject)));
        result.setContent(adventureDtos);
        result.setPages(adventuresPage.getTotalPages());
        result.setCurrentPage(adventuresPage.getNumber());
        result.setPageSize(adventuresPage.getSize());
        return result;
    }

    private AdventureDto mapAdventuresToDto(Adventure adventure) {
        AdventureDto adventureDto = modelMapper.map(adventure, AdventureDto.class);
        adventureDto.setIsDeletable(isAdventureDeletable(adventure));
        return adventureDto;
    }

    private boolean isAdventureDeletable(Adventure adventure) {
        AtomicBoolean isDeletable = new AtomicBoolean(true);
        adventure.getReservations().forEach(reservation -> {
            if (reservation.getInitDate().isAfter(ChronoLocalDateTime.from(LocalDateTime.now()))) {
                isDeletable.set(false);
            }
        });
        return isDeletable.get();
    }

}