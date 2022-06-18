package rs.ac.uns.ftn.siit.isa_mrs.service;

import org.springframework.http.ResponseEntity;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.InstructorDtos.ClientPerspectiveInstructorDtos.InstructorMenu.InstructorsForMenuDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.InstructorDtos.ClientPerspectiveInstructorDtos.InstructorProfile.ClientInstructorProfileDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.InstructorDtos.InstructorDtos.InstructorProfileDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.InstructorDtos.InstructorReservationsDtos.InstructorReservationDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;

import java.util.Collection;

public interface InstructorService {
    ResponseEntity<Collection<InstructorReservationDto>> getAllInstructrorReservations(String token);

    ResponseEntity<ClientInstructorProfileDto> getInstructor(Long id);

    ResponseEntity<InstructorProfileDto> getInstructor(String token);

    ResponseEntity<PageDto<InstructorsForMenuDto>> findInstructorsWithPaginationSortedByField(
            int offset, int pageSize, String field);
}
