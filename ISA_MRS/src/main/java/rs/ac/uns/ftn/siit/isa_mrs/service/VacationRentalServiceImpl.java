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
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.VacationRentalDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.VacationRental;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;
import rs.ac.uns.ftn.siit.isa_mrs.repository.VacationRentalRepo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class VacationRentalServiceImpl implements VacationRentalService{

    private final VacationRentalRepo vacationRentalRepo;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<VacationRentalDto> getVacationRental(Long id) {
        try{
            Optional<VacationRental> rental = vacationRentalRepo.findById(id);
            return rental.map(vacationRental -> new ResponseEntity<>(modelMapper.map(vacationRental, VacationRentalDto.class), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<PageDto<VacationRentalDto>> findVacationRentalsWithPaginationSortedByField(
            int offset, int pageSize, String field) {
        PageDto<VacationRentalDto> result = new PageDto<>();
        try{
            Pageable pageable = PageRequest.of(offset, pageSize).withSort(Sort.by(field));
            log.info("Trazimo vikendice");
            Page<VacationRental> vacationRentalsPage = vacationRentalRepo
                    .findAllByRentalObjectType(RentalObjectType.VacationRental, pageable);
            log.info("Pronasli smo vikendice: {}", vacationRentalsPage.getNumberOfElements());
            Collection<VacationRentalDto> vacationRentalDtos = new ArrayList<>();
            vacationRentalsPage.getContent().forEach(vacationRental -> {
                vacationRentalDtos.add(modelMapper.map(vacationRental, VacationRentalDto.class));
                log.info("Nasli smo vikendicu: {}", vacationRental.getId());
            });
            result.setContent(vacationRentalDtos);
            result.setPages(vacationRentalsPage.getTotalPages());
            result.setCurrentPage(vacationRentalsPage.getNumber() + 1);
            result.setPageSize(vacationRentalsPage.getSize());
            if (vacationRentalsPage.getContent().isEmpty()) {
                log.info("Status: no content");
                return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
            }
            else {
                log.info("Status: ok");
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }
}
