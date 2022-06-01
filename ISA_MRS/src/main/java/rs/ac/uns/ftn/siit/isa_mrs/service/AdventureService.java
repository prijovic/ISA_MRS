package rs.ac.uns.ftn.siit.isa_mrs.service;

import org.springframework.http.ResponseEntity;
import rs.ac.uns.ftn.siit.isa_mrs.dto.AdventureDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.AdventureDtos.AdventureProfileDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.AdventureDtos.AdventuresForMenuDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.VacationRentalDtos.VacationRentalProfileDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.VacationRentalDto;

import java.util.List;


public interface AdventureService {
    ResponseEntity<AdventureProfileDto> getAdventure(Long id, int page, int pageSize, String token);
    ResponseEntity<PageDto<AdventuresForMenuDto>> findAdventuresWithPaginationSortedByField(int offset, int pageSize,
                                                                                            String field);
    ResponseEntity<PageDto<AdventureDto>> findAdventuresWithPaginationSortedByFieldAndFilteredByOwner(int offset, int pageSize,
                                                                                    String field, String token);
    ResponseEntity<AdventureDto> findAdventure(Long id);

    ResponseEntity<AdventureDto> addAdventure(rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.AdventureDto adventureDto, String token);
    ResponseEntity<AdventureDto> addAdventurePhotos(Long id, List<String> photos);
}