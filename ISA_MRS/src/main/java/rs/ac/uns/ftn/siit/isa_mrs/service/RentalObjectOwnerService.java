package rs.ac.uns.ftn.siit.isa_mrs.service;

import org.springframework.http.ResponseEntity;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.BoatOwnerDtos.BoatOwnerProfileDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.VacationRentalOwnerDtos.VacationRentalOwnerProfileDto;

public interface RentalObjectOwnerService {
    long countRentalObjectOwners();
    ResponseEntity<BoatOwnerProfileDto> getBoatOwner(String token);
    ResponseEntity<VacationRentalOwnerProfileDto> getVacationRentalOwner(String token);
}
