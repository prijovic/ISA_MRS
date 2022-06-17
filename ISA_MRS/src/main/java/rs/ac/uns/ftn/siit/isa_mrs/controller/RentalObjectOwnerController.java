package rs.ac.uns.ftn.siit.isa_mrs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.InstructorDtos.InstructorMenu.InstructorsForMenuDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.InstructorDtos.InstructorProfile.InstructorProfileDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.VacationRentalDtos.VacationRentalProfileDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.VacationRentalDtos.VacationRentalsForMenuDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.service.InstructorService;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static rs.ac.uns.ftn.siit.isa_mrs.util.Paths.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(OWNER_CONTROLLER)
public class RentalObjectOwnerController {

    private final InstructorService instructorService;

    @GetMapping(GET_INSTRUCTORS)
    public ResponseEntity<PageDto<InstructorsForMenuDto>> getInstructorsWithPaginationAndSort(
            @RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam String field) {
        return instructorService.findInstructorsWithPaginationSortedByField(page, pageSize, field);
    }

    @GetMapping(GET_INSTRUCTOR)
    public ResponseEntity<InstructorProfileDto> getInstructor(@RequestParam Long id) {
        return instructorService.getInstructor(id);
    }
}
