package rs.ac.uns.ftn.siit.isa_mrs.service;

import org.springframework.http.ResponseEntity;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BoatDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;

public interface BoatService {
    ResponseEntity<BoatDto> getBoat(Long id);
    ResponseEntity<PageDto<BoatDto>> findBoatsWithPaginationSortedByField(int offset, int pageSize, String field);
}
