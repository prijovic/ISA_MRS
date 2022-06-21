package rs.ac.uns.ftn.siit.isa_mrs.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.siit.isa_mrs.dto.AdventureDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.AdventureDtos.AdventureProfileDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.AdventureDtos.AdventuresForMenuDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.BoatDtos.BoatProfileDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.BoatDtos.BoatsForMenuDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.VacationRentalDtos.VacationRentalProfileDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.VacationRentalDtos.VacationRentalsForMenuDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BoatDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.RentalPhotosDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.SpecialOfferDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.SubscribingDtos.SubscriptionDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.VacationRentalDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.*;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.IdListWrapperClass;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.UserType;
import rs.ac.uns.ftn.siit.isa_mrs.service.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

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
    private final SpecialOfferService specialOfferService;

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
    public ResponseEntity<VacationRentalProfileDto> getVacationRental(@RequestParam Long id, @RequestParam int page,
                                                          @RequestParam int pageSize, HttpServletRequest request) {
        return vacationRentalService.getVacationRental(id, page, pageSize, request.getHeader(AUTHORIZATION));
    }

    @GetMapping(GET_VACATION_RENTALS)
    public ResponseEntity<PageDto<VacationRentalsForMenuDto>> getVacationRentalsWithPaginationAndSort(
            @RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam String field) {
        return vacationRentalService.findVacationRentalsWithPaginationSortedByField(page, pageSize, field);
    }

    @GetMapping(GET_VACATION_RENTALS + "Owner")
    public ResponseEntity<PageDto<VacationRentalDto>> getVacationRentalsForOwner(
            @RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam String field, HttpServletRequest request) {
        return vacationRentalService.findVacationRentalsWithPaginationSortedByFieldAndFilteredByOwner(page, pageSize, field, request.getHeader(AUTHORIZATION));
    }

    @GetMapping(GET_BOAT)
    public ResponseEntity<BoatProfileDto> getBoat(@RequestParam Long id, @RequestParam int page,
                                                  @RequestParam int pageSize, HttpServletRequest request) {
        return boatService.getBoat(id, page, pageSize, request.getHeader(AUTHORIZATION));
    }

    @GetMapping(GET_BOATS)
    public ResponseEntity<PageDto<BoatsForMenuDto>> getBoatsWithPaginationAndSort(
            @RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam String field) {
        return boatService.findBoatsWithPaginationSortedByField(page, pageSize, field);
    }

    @GetMapping(GET_BOATS + "Owner")
    public ResponseEntity<PageDto<BoatDto>> getBoatsForOwner(
            @RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam String field, HttpServletRequest request) {
        log.info("Uslo u kontroler");
        return boatService.findBoatsWithPaginationSortedByFieldAndFilteredByOwner(page, pageSize, field, request.getHeader(AUTHORIZATION));
    }

    @GetMapping(GET_ADVENTURE)
    public ResponseEntity<AdventureProfileDto> getAdventure(@RequestParam Long id, @RequestParam int page,
                                                            @RequestParam int pageSize, HttpServletRequest request) {
        return adventureService.getAdventure(id, page, pageSize, request.getHeader(AUTHORIZATION));
    }

    @GetMapping(GET_ADVENTURES)
    public ResponseEntity<PageDto<AdventuresForMenuDto>> getAdventuresWithPaginationAndSort(
            @RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam String field) {
        return adventureService.findAdventuresWithPaginationSortedByField(page, pageSize, field);
    }

    @GetMapping(GET_ADVENTURES + "Instructor")
    public ResponseEntity<PageDto<AdventureDto>> getAdventuresForInstructor(
            @RequestParam Integer page, @RequestParam Integer pageSize,
            @RequestParam String field, HttpServletRequest request){
        return adventureService.findAdventuresWithPaginationSortedByFieldAndFilteredByOwner(page, pageSize, field, request.getHeader(AUTHORIZATION));
    }

    @GetMapping(GET_ADVENTURE + "Instructor")
    public ResponseEntity<AdventureDto> getAdventure(@RequestParam Long id) {
        return adventureService.findAdventure(id);
    }

    @PutMapping("/updateAdventure")
    public ResponseEntity<Long> updateAdventure(@RequestBody rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.AdventureDto adventure) {
        return adventureService.updateAdventure(adventure);
    }

    @PostMapping("/addAdventure")
    public ResponseEntity<AdventureDto> addAdventure(@RequestBody rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.AdventureDto adventure, HttpServletRequest request) {
        return adventureService.addAdventure(adventure, request.getHeader(AUTHORIZATION));
    }

    @PostMapping("/connectPhotosToRental")
    public ResponseEntity<AdventureDto> connectPhotosToRental(@RequestBody RentalPhotosDto dto) {
        return adventureService.addAdventurePhotos(dto.getId(), dto.getPhotos());
    }

    @PostMapping(ADD_SUBSCRIBER)
    public ResponseEntity<Void> addSubscriber(@RequestBody SubscriptionDto sd, HttpServletRequest request) {
        return rentalObjectService.addSubscriber(sd.getRentalId(), request.getHeader(AUTHORIZATION));
    }

    @PostMapping(CANCEL_SUBSCRIPTION)
    public ResponseEntity<Void> cancelSubscription(@RequestBody SubscriptionDto sd, HttpServletRequest request) {
        return rentalObjectService.cancelSubscription(sd.getRentalId(), request.getHeader(AUTHORIZATION));
    }

    @PostMapping("/addSpecialOffer")
    public ResponseEntity<RentalObjectDto> addSpecialOffer(@RequestBody SpecialOfferDto dto) {
        return specialOfferService.addSpecialOffer(dto);
    }

    @Data
    static class PeriodsSettingForm {
        private Long id;
        private List<LocalDate> dates;
    }
}
