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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.siit.isa_mrs.dto.AdventureDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.Adventure;
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
    public Optional<Adventure> getAdventure(Long id) {
        log.info("Getting adventure by id {}.", id);
        return adventureRepo.findById(id);
    }

    @Override
    public ResponseEntity<PageDto<AdventureDto>> findAdventuresWithPaginationSortedByField(int offset, int pageSize,
                                                                                           String field) {
        PageDto<AdventureDto> result = new PageDto<>();
        try{
            Pageable pageable = PageRequest.of(offset, pageSize).withSort(Sort.by(field));
            Page<Adventure> adventuresPage = adventureRepo.findByRentalObjectType(RentalObjectType.Adventure, pageable);
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
        } catch (Exception e) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

}