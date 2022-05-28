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
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.RentalObjectDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.Adventure;
import rs.ac.uns.ftn.siit.isa_mrs.model.RentalObject;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;
import rs.ac.uns.ftn.siit.isa_mrs.repository.AdventureRepo;
import rs.ac.uns.ftn.siit.isa_mrs.security.JwtDecoder;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AdventureServiceImpl implements AdventureService{
    private final AdventureRepo adventureRepo;
    private final ModelMapper modelMapper;
    private final JwtDecoder jwtDecoder;

    @Override
    public ResponseEntity<PageDto<AdventureDto>> findAdventuresWithPaginationSortedByField(int offset, int pageSize, String field) {
        try{
            Pageable pageable = PageRequest.of(offset, pageSize).withSort(Sort.by(field));
            Page<Adventure> adventuresPage = adventureRepo.findByRentalObjectType(RentalObjectType.Adventure, pageable);
            return new ResponseEntity<>(packAdventures(adventuresPage), HttpStatus.OK);
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
        adventureDto.setIsDeletable(adventure.getReservations().size() == 0);
        return adventureDto;
    }

}