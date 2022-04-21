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
@Transactional
@Slf4j
public class VacationRentalServiceImpl implements VacationRentalService{

    private final VacationRentalRepo vacationRentalRepo;
    private final ModelMapper modelMapper;


    @Override
    public Optional<VacationRental> getVacationRental(Long id) {
        log.info("Getting vacation rental by id {}.", id);
        return vacationRentalRepo.findById(id);
    }

    @Override
    public ResponseEntity<PageDto<VacationRentalDto>> findVacationRentalsWithPaginationSortedByField(
            int offset, int pageSize, String field) {
        PageDto<VacationRentalDto> result = new PageDto<>();
        try{
            Pageable pageable = PageRequest.of(offset, pageSize).withSort(Sort.by(field));
            Page<VacationRental> vacationRentalsPage = vacationRentalRepo
                    .findByRentalObjectType(RentalObjectType.VacationRental, pageable);
            Collection<VacationRentalDto> vacationRentalDtos = new ArrayList<>();
            vacationRentalsPage.getContent().forEach(vacationRental -> vacationRentalDtos
                    .add(modelMapper.map(vacationRental, VacationRentalDto.class)));
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
}
