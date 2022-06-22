package rs.ac.uns.ftn.siit.isa_mrs.service;

import org.springframework.http.ResponseEntity;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.BoatOwnerDtos.BoatOwnerProfileDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.BoatOwnerDtos.BoatOwnerReservationDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.VacationRentalOwnerDtos.VacationRentalOwnerProfileDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.VacationRentalOwnerDtos.VacationRentalOwnerReservationDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.ClientDtos.ClientReservationLimitsDto;

import java.util.Collection;

public interface RentalObjectOwnerService {
    long countRentalObjectOwners();
    ResponseEntity<BoatOwnerProfileDto> getBoatOwner(String token);
    ResponseEntity<VacationRentalOwnerProfileDto> getVacationRentalOwner(String token);
    ResponseEntity<Collection<BoatOwnerReservationDto>> getAllBoatOwnerReservations(String token);
    ResponseEntity<Collection<VacationRentalOwnerReservationDto>> getAllVacationRentalOwnerReservations(String token);
    ResponseEntity<ClientReservationLimitsDto> getReservationLimits(long rentalId, long ownerId);
}
