package rs.ac.uns.ftn.siit.isa_mrs.service;

import org.springframework.http.ResponseEntity;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.VacationRentalDtos.VacationRentalProfileDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.VacationRentalDtos.VacationRentalsForMenuDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.AddVacationRentalDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.VacationRentalDto;

public interface VacationRentalService {
    ResponseEntity<VacationRentalProfileDto> getVacationRental(Long id, int page, int pageSize, String token);
    ResponseEntity<PageDto<VacationRentalsForMenuDto>> findVacationRentalsWithPaginationSortedByField(int offset, int pageSize,
                                                                                                      String field);
    ResponseEntity<VacationRentalDto> addNewVacationRental(AddVacationRentalDto vacationRental);
}
