package rs.ac.uns.ftn.siit.isa_mrs.service;

import org.springframework.http.ResponseEntity;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.InstructorReservationDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.OwnerReservationDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.ReportDtos.AddInstructorReportDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.ReportDtos.AddReportDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.ReservationDtos.ClientBookDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.ReviewDtos.AddInstructorReviewDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.ReviewDtos.AddReviewDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.ReservationDto;

import java.util.Collection;

public interface ReservationService {
    ResponseEntity<Void> bookSpecialOffer(String token, Long offerId, double total);

    ResponseEntity<Void> cancelReservation(Long id, double feeAmount);
    ResponseEntity<Collection<ReservationDto>> getFutureReservations(String token);
    ResponseEntity<Void> addReview(AddReviewDto srd, String token);
    long countReservationsLastYear();
    long countReservationsLastMonth();
    long countReservationsLastWeek();
    long countReservations();

    ResponseEntity<Void> addInstructorReview(AddInstructorReviewDto aird, String token);

    ResponseEntity<Void> addReport(AddReportDto ard, String token);

    ResponseEntity<Void> addInstructorReport(AddInstructorReportDto aird, String token);

    ResponseEntity<Void> book(ClientBookDto crd, String token);

    ResponseEntity<Void> bookForClient(InstructorReservationDto dto);
    ResponseEntity<Void> ownerBookForClient(OwnerReservationDto dto);
}
