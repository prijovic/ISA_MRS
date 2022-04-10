package rs.ac.uns.ftn.siit.isa_mrs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.siit.isa_mrs.exception.RentalNotFound;
import rs.ac.uns.ftn.siit.isa_mrs.model.VacationRental;
import rs.ac.uns.ftn.siit.isa_mrs.service.VacationRentalService;

import java.util.Collection;
import java.util.Optional;

import static rs.ac.uns.ftn.siit.isa_mrs.util.Paths.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(RENTAL_OBJECT_CONTROLLER)
public class RentalObjectController {

    private VacationRentalService vacationRentalService;

    @GetMapping(GET_VACATION_RENTAL)
    public VacationRental getVacationRental(Long id) {
        Optional<VacationRental> vacationRental = vacationRentalService.getVacationRental(id);
        if (vacationRental.isPresent()) {
            return vacationRental.get();
        }
        else {
            throw new RentalNotFound(HttpStatus.NOT_FOUND, "id: " + id);
        }
    }

    @GetMapping(GET_VACATION_RENTALS)
    public Collection<VacationRental> getVacationRentals() {
        return vacationRentalService.getVacationRentals();
    }

}
