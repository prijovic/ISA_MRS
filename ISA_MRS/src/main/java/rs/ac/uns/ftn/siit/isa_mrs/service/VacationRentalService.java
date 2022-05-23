package rs.ac.uns.ftn.siit.isa_mrs.service;

import org.springframework.http.ResponseEntity;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.AddVacationRentalDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.VacationRentalDto;

public interface VacationRentalService {
    ResponseEntity<VacationRentalDto> getVacationRental(Long id);
    ResponseEntity<PageDto<VacationRentalDto>> findVacationRentalsWithPaginationSortedByField(int offset, int pageSize,
                                                                                              String field);
    ResponseEntity<PageDto<VacationRentalDto>> findVacationRentalsWithPaginationSortedByFieldAndFilteredByOwner(int offset, int pageSize,
                                                                                                                String field, String ownerEmail);
    ResponseEntity<VacationRentalDto> addNewVacationRental(AddVacationRentalDto vacationRental);
}
