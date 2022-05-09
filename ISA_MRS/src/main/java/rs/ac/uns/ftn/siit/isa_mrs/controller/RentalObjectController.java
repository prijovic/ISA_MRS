package rs.ac.uns.ftn.siit.isa_mrs.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.siit.isa_mrs.dto.AdventureDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BoatDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.VacationRentalDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.RentalObjectPeriodsDto;
import rs.ac.uns.ftn.siit.isa_mrs.exception.RentalNotFound;
import rs.ac.uns.ftn.siit.isa_mrs.model.Adventure;
import rs.ac.uns.ftn.siit.isa_mrs.model.Boat;
import rs.ac.uns.ftn.siit.isa_mrs.model.VacationRental;
import rs.ac.uns.ftn.siit.isa_mrs.service.AdventureService;
import rs.ac.uns.ftn.siit.isa_mrs.service.BoatService;
import rs.ac.uns.ftn.siit.isa_mrs.service.RentalObjectService;
import rs.ac.uns.ftn.siit.isa_mrs.service.VacationRentalService;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static rs.ac.uns.ftn.siit.isa_mrs.util.Paths.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(RENTAL_OBJECT_CONTROLLER)
public class RentalObjectController {
    private final RentalObjectService rentalObjectService;
    private VacationRentalService vacationRentalService;
    private BoatService boatService;
    private final AdventureService adventureService;
    private final ModelMapper modelMapper;

    @GetMapping(GET_VACATION_RENTAL)
    public VacationRental getVacationRental(@RequestParam Long id) {
        Optional<VacationRental> vacationRental = vacationRentalService.getVacationRental(id);
        if (vacationRental.isPresent()) {
            return vacationRental.get();
        }
        else {
            throw new RentalNotFound(HttpStatus.NOT_FOUND, "id: " + id);
        }
    }

    @GetMapping(GET_VACATION_RENTALS)
    public ResponseEntity<PageDto<VacationRentalDto>> getVacationRentalsWithPaginationAndSort(
            @RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam String field) {
        return vacationRentalService.findVacationRentalsWithPaginationSortedByField(page, pageSize, field);
    }

    @GetMapping(GET_BOAT)
    public Boat getBoat(@RequestParam Long id) {
        Optional<Boat> boat = boatService.getBoat(id);
        if (boat.isPresent()) {
            return boat.get();
        }
        else {
            throw new RentalNotFound(HttpStatus.NOT_FOUND, "id: " + id);
        }
    }

    @GetMapping(GET_BOATS)
    public ResponseEntity<PageDto<BoatDto>> getBoatsWithPaginationAndSort(
            @RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam String field) {
        return boatService.findBoatsWithPaginationSortedByField(page, pageSize, field);
    }

    @GetMapping(GET_ADVENTURES)
    public ResponseEntity<PageDto<AdventureDto>> getAdventuresWithPaginationAndSort(
            @RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam String field) {
        return adventureService.findAdventuresWithPaginationSortedByField(page, pageSize, field);
    }

    @GetMapping(GET_ADVENTURES + "Instructor")
    public ResponseEntity<PageDto<AdventureDto>> getAdventuresForInstructor(
            @RequestParam Integer page, @RequestParam Integer pageSize,
            @RequestParam String field, @RequestParam String email) {
        return adventureService.findAdventuresWithPaginationSortedByFieldAndFilteredByOwner(page, pageSize, field, email);
    }

    @PostMapping(AVAILABILITY_PERIOD)
    public ResponseEntity<RentalObjectPeriodsDto> setPeriods(@RequestBody PeriodsSettingForm periodsSettingForm) {
        return rentalObjectService.setAvailabilityPeriods(periodsSettingForm.getId(), periodsSettingForm.getDates());
    }

    @Data
    static class PeriodsSettingForm{
        private Long id;
        private List<LocalDate> dates;
    }
}
