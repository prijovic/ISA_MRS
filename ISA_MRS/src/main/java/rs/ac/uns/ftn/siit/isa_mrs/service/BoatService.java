package rs.ac.uns.ftn.siit.isa_mrs.service;

import org.springframework.http.ResponseEntity;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BoatDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.Boat;

import java.util.Optional;

public interface BoatService {
    Optional<Boat> getBoat(Long id);
    ResponseEntity<PageDto<BoatDto>> findBoatsWithPaginationSortedByField(int offset, int pageSize, String field);
}
