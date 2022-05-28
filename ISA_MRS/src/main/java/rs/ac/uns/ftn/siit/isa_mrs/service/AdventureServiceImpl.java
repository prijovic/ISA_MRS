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
import rs.ac.uns.ftn.siit.isa_mrs.dto.BoatDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.VacationRentalDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.Adventure;
import rs.ac.uns.ftn.siit.isa_mrs.model.Boat;
import rs.ac.uns.ftn.siit.isa_mrs.model.VacationRental;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;
import rs.ac.uns.ftn.siit.isa_mrs.repository.AdventureRepo;

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

    @Override
    public ResponseEntity<AdventureDto> getAdventure(Long id) {
        try{
            log.info("Usli smo u servis, id je " + id);
            Optional<Adventure> adventure = adventureRepo.findById(id);
            if (adventure.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            log.info("Prosli nabavku");
            log.info(""+adventure);

            AdventureDto adventureDto = modelMapper.map(adventure, AdventureDto.class);
            log.info(""+adventureDto);
            return new ResponseEntity<>(adventureDto, HttpStatus.OK);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<PageDto<AdventureDto>> findAdventuresWithPaginationSortedByField(int offset, int pageSize,
                                                                                           String field) {
        PageDto<AdventureDto> result = new PageDto<>();
        try{
            Pageable pageable = PageRequest.of(offset, pageSize).withSort(Sort.by(field));
            Page<Adventure> adventuresPage = adventureRepo.findByRentalObjectType(RentalObjectType.Adventure, pageable);
            return getPageDtoResponseEntity(result, adventuresPage);
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

}