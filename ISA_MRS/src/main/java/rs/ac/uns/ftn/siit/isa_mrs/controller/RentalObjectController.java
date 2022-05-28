package rs.ac.uns.ftn.siit.isa_mrs.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.siit.isa_mrs.dto.*;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.AddVacationRentalDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.IdListWrapperClass;
import rs.ac.uns.ftn.siit.isa_mrs.exception.RentalNotFound;
import rs.ac.uns.ftn.siit.isa_mrs.model.Boat;
import rs.ac.uns.ftn.siit.isa_mrs.service.AdventureService;
import rs.ac.uns.ftn.siit.isa_mrs.service.BoatService;
import rs.ac.uns.ftn.siit.isa_mrs.service.RentalObjectService;
import rs.ac.uns.ftn.siit.isa_mrs.service.VacationRentalService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static rs.ac.uns.ftn.siit.isa_mrs.util.Paths.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(RENTAL_OBJECT_CONTROLLER)
public class RentalObjectController {
    private final RentalObjectService rentalObjectService;
    private final VacationRentalService vacationRentalService;
    private final BoatService boatService;
    private final AdventureService adventureService;

    @GetMapping("/getRentalObjectsPage")
    public ResponseEntity<PageDto<RentalObjectDto>> getRentalObjects(@RequestParam Integer page, @RequestParam Integer pageSize) {
        return rentalObjectService.getRentalObjects(page, pageSize);
    }

    @GetMapping("/getRentalObjectsFilterPage")
    public ResponseEntity<PageDto<RentalObjectDto>> getRentalObjects(@RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam String filter) {
        return rentalObjectService.getRentalObjects(page, pageSize, filter);
    }

    @PutMapping("/multipleRentalsStatusChange")
    public ResponseEntity<Collection<RentalObjectDto>> changeRentalObjectsStatus(@RequestBody IdListWrapperClass lwc) {
        return rentalObjectService.changeRentalObjectsStatus(lwc.getList());
    }

    @GetMapping(GET_VACATION_RENTAL)
    public ResponseEntity<VacationRentalDto> getVacationRental(@RequestParam Long id) {
        return vacationRentalService.getVacationRental(id);
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
            @RequestParam String field, HttpServletRequest request) {
        return adventureService.findAdventuresWithPaginationSortedByFieldAndFilteredByOwner(page, pageSize, field, request.getHeader(AUTHORIZATION));
    }

    @GetMapping("/getAdventure")
    public ResponseEntity<AdventureDto> getAdventure(@RequestParam Long id) {
        return adventureService.findAdventure(id);
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

    @PostMapping(ADD_VACATION_RENTAL)
    public ResponseEntity<VacationRentalDto> addVacationRental(@RequestBody AddVacationRentalDto avrd){
        log.info("Kontroler");
        return vacationRentalService.addNewVacationRental(avrd);
    }

}
