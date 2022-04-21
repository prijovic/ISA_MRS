package rs.ac.uns.ftn.siit.isa_mrs.service;

import org.springframework.http.ResponseEntity;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.VacationRentalDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.VacationRental;

import java.util.Optional;

public interface VacationRentalService {

    Optional<VacationRental> getVacationRental(Long id);
    ResponseEntity<PageDto<VacationRentalDto>> findVacationRentalsWithPaginationSortedByField(int offset, int pageSize,
                                                                                              String field);
}
