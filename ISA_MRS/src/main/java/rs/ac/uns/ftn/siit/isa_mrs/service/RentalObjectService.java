package rs.ac.uns.ftn.siit.isa_mrs.service;

import org.springframework.http.ResponseEntity;
import rs.ac.uns.ftn.siit.isa_mrs.dto.RentalObjectPeriodsDto;

import java.time.LocalDate;
import java.util.List;

public interface RentalObjectService {
    ResponseEntity<RentalObjectPeriodsDto> setAvailabilityPeriods(Long id, List<LocalDate> dates);
}
