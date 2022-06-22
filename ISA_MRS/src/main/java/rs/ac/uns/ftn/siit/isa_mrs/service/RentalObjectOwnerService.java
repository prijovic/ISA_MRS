package rs.ac.uns.ftn.siit.isa_mrs.service;

import org.springframework.http.ResponseEntity;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.ClientDtos.ClientReservationLimitsDto;

public interface RentalObjectOwnerService {
    long countRentalObjectOwners();

    ResponseEntity<ClientReservationLimitsDto> getReservationLimits(long rentalId, long ownerId);
}
