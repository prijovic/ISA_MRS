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
import rs.ac.uns.ftn.siit.isa_mrs.dto.BoatDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.VacationRentalDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.Boat;
import rs.ac.uns.ftn.siit.isa_mrs.model.VacationRental;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;
import rs.ac.uns.ftn.siit.isa_mrs.repository.BoatRepo;

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

    @Override
    public ResponseEntity<BoatDto> getBoat(Long id) {
        try{
            log.info("Usli smo u servis, id je " + id);
            Optional<Boat> boat = boatRepo.findById(id);
            if (boat.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            log.info("Prosli nabavku");
            log.info(""+boat);

            BoatDto boatDto = modelMapper.map(boat, BoatDto.class);
            log.info(""+boatDto);
            return new ResponseEntity<>(boatDto, HttpStatus.OK);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<PageDto<BoatDto>> findBoatsWithPaginationSortedByField(int offset, int pageSize,
                                                                                 String field) {
        PageDto<BoatDto> result = new PageDto<>();
        try{
            Pageable pageable = PageRequest.of(offset, pageSize).withSort(Sort.by(field));
            Page<Boat> boatsPage = boatRepo.findByRentalObjectType(RentalObjectType.Boat, pageable);
            Collection<BoatDto> boatDtos = new ArrayList<>();
            boatsPage.getContent().forEach(boat -> boatDtos.add(modelMapper.map(boat, BoatDto.class)));
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

}
