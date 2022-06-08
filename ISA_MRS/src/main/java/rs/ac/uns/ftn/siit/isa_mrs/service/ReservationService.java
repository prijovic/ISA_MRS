package rs.ac.uns.ftn.siit.isa_mrs.service;

import org.springframework.http.ResponseEntity;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.ReviewDtos.SaveReviewDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.ReservationDto;

import java.util.Collection;

public interface ReservationService {
    ResponseEntity<Void> cancelReservation(Long id);
    ResponseEntity<Collection<ReservationDto>> getFutureReservations(String token);
    ResponseEntity<Void> addReview(SaveReviewDto srd, String token);
}
