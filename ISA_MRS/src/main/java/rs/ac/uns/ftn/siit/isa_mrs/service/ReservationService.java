package rs.ac.uns.ftn.siit.isa_mrs.service;

import org.springframework.http.ResponseEntity;
import rs.ac.uns.ftn.siit.isa_mrs.dto.ReservationDto;

import java.util.Collection;

public interface ReservationService {
    ResponseEntity<Collection<ReservationDto>> getFutureReservations(String token);
}
