package rs.ac.uns.ftn.siit.isa_mrs.service;

import org.springframework.http.ResponseEntity;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.InstructorDtos.InstructorMenu.InstructorsForMenuDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.InstructorDtos.InstructorProfile.InstructorProfileDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;

public interface InstructorService {
    ResponseEntity<InstructorProfileDto> getInstructor(Long id);

    ResponseEntity<PageDto<InstructorsForMenuDto>> findInstructorsWithPaginationSortedByField(
            int offset, int pageSize, String field);
}
