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
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;
import rs.ac.uns.ftn.siit.isa_mrs.repository.AdventureRepo;
import rs.ac.uns.ftn.siit.isa_mrs.repository.ClientRepo;
import rs.ac.uns.ftn.siit.isa_mrs.security.JwtDecoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AdventureServiceImpl implements AdventureService{

    private final AdventureRepo adventureRepo;
    private final ModelMapper modelMapper;
    private final RentalObjectServiceImpl rentalService;
    private final ClientRepo clientRepo;
    private final JwtDecoder jwtDecoder;

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
            Optional<Client> optionalClient = clientRepo.findByEmail(decodedToken.getEmail());
            if(optionalClient.isPresent()){
                Client client = optionalClient.get();
                if(adventure.getSubscribers().contains(client)) adventureDto.setIsUserSubscribed(true);
            }
            return new ResponseEntity<>(adventureDto, HttpStatus.OK);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<PageDto<AdventuresForMenuDto>> findAdventuresWithPaginationSortedByField(int offset, int pageSize,
                                                                                           String field) {
        PageDto<AdventuresForMenuDto> result = new PageDto<>();
        try{
            Pageable pageable = PageRequest.of(offset, pageSize).withSort(Sort.by(field));
            Page<Adventure> adventuresPage = adventureRepo.findByRentalObjectType(RentalObjectType.Adventure, pageable);
            Collection<AdventuresForMenuDto> adventureDtos = new ArrayList<>();
            adventuresPage.getContent().forEach(adventure ->
                    adventureDtos.add(setUpMenuDto(adventure)));
            result.setContent(adventureDtos);
            result.setPages(adventuresPage.getTotalPages());
            result.setCurrentPage(adventuresPage.getNumber() + 1);
            result.setPageSize(adventuresPage.getSize());
            if (adventuresPage.getContent().isEmpty()) {
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
    public ResponseEntity<PageDto<AdventureDto>> findAdventuresWithPaginationSortedByFieldAndFilteredByOwner(int offset, int pageSize, String field, String ownerEmail) {
        PageDto<AdventureDto> result = new PageDto<>();
        try{
            Pageable pageable = PageRequest.of(offset, pageSize).withSort(Sort.by(field));
            Page<Adventure> adventuresPage = adventureRepo.findAllByRentalObjectTypeAndRentalObjectOwnerEmail(RentalObjectType.Adventure, ownerEmail, pageable);
            return getPageDtoResponseEntity(result, adventuresPage);
        } catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @NotNull
    private ResponseEntity<PageDto<AdventureDto>> getPageDtoResponseEntity(PageDto<AdventureDto> result, Page<Adventure> adventuresPage) {
        Collection<AdventureDto> adventureDtos = new ArrayList<>();
        adventuresPage.getContent().forEach(adventure ->
                adventureDtos.add(modelMapper.map(adventure, AdventureDto.class)));
        result.setContent(adventureDtos);
        result.setPages(adventuresPage.getTotalPages());
        result.setCurrentPage(adventuresPage.getNumber() + 1);
        result.setPageSize(adventuresPage.getSize());
        if (adventuresPage.getContent().isEmpty()) {
            return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    private @NotNull AdventuresForMenuDto setUpMenuDto(Adventure adventure) {
        AdventuresForMenuDto adventureDto = modelMapper.map(adventure, AdventuresForMenuDto.class);
        adventureDto.setGrade(rentalService.calculateRentalRating(adventure));
        if(adventure.getPhotos().size() != 0) {
            Optional<Photo> photo = adventure.getPhotos().stream().findFirst();
            photo.ifPresent(value -> adventureDto.setDisplayPhoto(modelMapper.map(value, PhotoDto.class)));
        }
        return adventureDto;
    }

}