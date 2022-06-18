package rs.ac.uns.ftn.siit.isa_mrs.service;

import org.springframework.http.ResponseEntity;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.ReportDtos.AddReportDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.ReviewDtos.AddReviewDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.ReservationDto;

import java.util.Collection;

public interface ReservationService {
    ResponseEntity<Void> cancelReservation(Long id);
    ResponseEntity<Collection<ReservationDto>> getFutureReservations(String token);
    ResponseEntity<Void> addReview(AddReviewDto srd, String token);
    ResponseEntity<Void> addReport(AddReportDto ard, String token);
    long countReservationsLastYear();
    long countReservationsLastMonth();
    long countReservationsLastWeek();
    long countReservations();
}
