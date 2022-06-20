package rs.ac.uns.ftn.siit.isa_mrs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.BoatOwnerDtos.BoatOwnerProfileDto;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.InstructorDtos.ClientPerspectiveInstructorDtos.InstructorMenu.InstructorsForMenuDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.InstructorDtos.ClientPerspectiveInstructorDtos.InstructorProfile.ClientInstructorProfileDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.InstructorDtos.InstructorDtos.InstructorProfileDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.VacationRentalOwnerDtos.VacationRentalOwnerProfileDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.service.InstructorService;
import rs.ac.uns.ftn.siit.isa_mrs.service.RentalObjectOwnerService;

import javax.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static rs.ac.uns.ftn.siit.isa_mrs.util.Paths.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(OWNER_CONTROLLER)
public class RentalObjectOwnerController {

    private final InstructorService instructorService;
    private final RentalObjectOwnerService rentalObjectOwnerService;

    @GetMapping(GET_INSTRUCTORS)
    public ResponseEntity<PageDto<InstructorsForMenuDto>> getInstructorsWithPaginationAndSort(
            @RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam String field) {
        return instructorService.findInstructorsWithPaginationSortedByField(page, pageSize, field);
    }

    @GetMapping(GET_INSTRUCTOR_FOR_CLIENT)
    public ResponseEntity<ClientInstructorProfileDto> getInstructor(@RequestParam Long id) {
        return instructorService.getInstructor(id);
    }

    @GetMapping(GET_INSTRUCTOR)
    public ResponseEntity<InstructorProfileDto> getInstructor(HttpServletRequest request) {
        return instructorService.getInstructor(request.getHeader(AUTHORIZATION));
    }
    
    @GetMapping(GET_BOAT_OWNER)
    public ResponseEntity<BoatOwnerProfileDto> getBoatOwner(HttpServletRequest request) {
        return rentalObjectOwnerService.getBoatOwner(request.getHeader(AUTHORIZATION));
    }

    @GetMapping(GET_VACATION_RENTAL_OWNER)
    public ResponseEntity<VacationRentalOwnerProfileDto> getVacationRentalOwner(HttpServletRequest request) {
        return rentalObjectOwnerService.getVacationRentalOwner(request.getHeader(AUTHORIZATION));
    }

    @PutMapping("/updateInstructorPeriod")
    public ResponseEntity<InstructorProfileDto> updateInstructorPeriod(@RequestParam String start, @RequestParam String end, HttpServletRequest request) {
        return instructorService.updateInstructorPeriod(start, end, request.getHeader(AUTHORIZATION));
    }
}
