package rs.ac.uns.ftn.siit.isa_mrs.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.siit.isa_mrs.dto.AdventureDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.VacationRentalDtos.VacationRentalProfileDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.VacationRentalDtos.VacationRentalsForMenuDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BoatDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.AddVacationRentalDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.SubscribingDtos.addSubscriberDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.VacationRentalDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.RentalObjectPeriodsDto;
import rs.ac.uns.ftn.siit.isa_mrs.service.AdventureService;
import rs.ac.uns.ftn.siit.isa_mrs.service.BoatService;
import rs.ac.uns.ftn.siit.isa_mrs.service.RentalObjectService;
import rs.ac.uns.ftn.siit.isa_mrs.service.VacationRentalService;

import java.time.LocalDate;
import java.util.List;

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
    private final ModelMapper modelMapper;

    @GetMapping(GET_VACATION_RENTAL)
    public ResponseEntity<VacationRentalProfileDto> getVacationRental(@RequestParam Long id, @RequestParam String email) {
        return vacationRentalService.getVacationRental(id, email);
    }

    @GetMapping(GET_VACATION_RENTALS)
    public ResponseEntity<PageDto<VacationRentalsForMenuDto>> getVacationRentalsWithPaginationAndSort(
            @RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam String field) {
        return vacationRentalService.findVacationRentalsWithPaginationSortedByField(page, pageSize, field);
    }

    @GetMapping(GET_BOAT)
    public ResponseEntity<BoatDto> getBoat(@RequestParam Long id) {
        return boatService.getBoat(id);
    }

    @GetMapping(GET_BOATS)
    public ResponseEntity<PageDto<BoatDto>> getBoatsWithPaginationAndSort(
            @RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam String field) {
        return boatService.findBoatsWithPaginationSortedByField(page, pageSize, field);
    }

    @GetMapping(GET_ADVENTURE)
    public ResponseEntity<AdventureDto> getAdventure(@RequestParam Long id) {
        return adventureService.getAdventure(id);
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

    @PostMapping(ADD_SUBSCRIBER)
    public ResponseEntity<Void> addSubscriber(@RequestBody addSubscriberDto asd) {
        return rentalObjectService.addSubscriber(asd.getRentalId(), asd.getClientEmail());
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
