package rs.ac.uns.ftn.siit.isa_mrs.service;

import org.springframework.http.ResponseEntity;
import rs.ac.uns.ftn.siit.isa_mrs.dto.AdventureDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.VacationRentalDto;


public interface AdventureService {
    ResponseEntity<AdventureDto> getAdventure(Long id);
    ResponseEntity<PageDto<AdventureDto>> findAdventuresWithPaginationSortedByField(int offset, int pageSize,
                                                                                    String field);
    ResponseEntity<PageDto<AdventureDto>> findAdventuresWithPaginationSortedByFieldAndFilteredByOwner(int offset, int pageSize,
                                                                                    String field, String ownerEmail);
}