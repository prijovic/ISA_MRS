package rs.ac.uns.ftn.siit.isa_mrs.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.RentalObjectDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.RentalObjectPeriodsDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.UserDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.ReviewDtos.ReviewDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.RentalObjectPeriodsDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.RentalObject;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface RentalObjectService {
    ResponseEntity<RentalObjectPeriodsDto> setAvailabilityPeriods(Long id, List<LocalDate> dates);
    ResponseEntity<PageDto<RentalObjectDto>> getRentalObjects(int page, int pageSize);
    ResponseEntity<Collection<RentalObjectDto>> changeRentalObjectsStatus(Collection<Long> ids);
    ResponseEntity<PageDto<RentalObjectDto>> getRentalObjects(int page, int pageSize, String filter);
    ResponseEntity<Void> addSubscriber(Long rentalId, String email);
    String calculateRentalRating(RentalObject rental);
    Collection<ReviewDto> getRentalReviews(RentalObject rental);
}
