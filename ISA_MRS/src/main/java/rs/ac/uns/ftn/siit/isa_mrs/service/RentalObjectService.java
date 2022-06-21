package rs.ac.uns.ftn.siit.isa_mrs.service;

import org.springframework.http.ResponseEntity;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.AdminDtos.GraphDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.InstructorDtos.RentalGradeDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.RentalObjectDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.ReviewDtos.ReviewDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.RentalObject;
import rs.ac.uns.ftn.siit.isa_mrs.model.RentalObjectOwner;

import java.util.Collection;

public interface RentalObjectService {
//    ResponseEntity<RentalObjectPeriodsDto> setAvailabilityPeriods(Long id, List<LocalDate> dates);
    ResponseEntity<PageDto<RentalObjectDto>> getRentalObjects(int page, int pageSize);
    ResponseEntity<Collection<RentalObjectDto>> changeRentalObjectsStatus(Collection<Long> ids);    ResponseEntity<PageDto<RentalObjectDto>> getRentalObjects(int page, int pageSize, String filter);
    ResponseEntity<Void> addSubscriber(Long rentalId, String email);
    String calculateRentalRating(RentalObject rental);
    PageDto<ReviewDto> getRentalReviews(RentalObject rental, int page, int pageSize);
    String calculateOwnerRating(RentalObjectOwner owner);
    ResponseEntity<Void> cancelSubscription(Long rentalId, String token);
    Collection<RentalGradeDto> getRentalsGrades(String token);
    GraphDto getLastYearRentalReservationsGraph(String token);
    GraphDto getLastMonthRentalReservationsGraph(String token);
    GraphDto getLastWeekRentalReservationsGraph(String token);
}
