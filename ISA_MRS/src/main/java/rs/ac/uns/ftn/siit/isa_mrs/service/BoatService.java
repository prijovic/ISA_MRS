package rs.ac.uns.ftn.siit.isa_mrs.service;

import org.springframework.http.ResponseEntity;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.BoatDtos.BoatProfileDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.BoatDtos.BoatsForMenuDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BoatDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.AddBoatDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;

import java.util.List;

public interface BoatService {
    ResponseEntity<BoatProfileDto> getBoat(Long id, int page, int pageSize, String token);
    ResponseEntity<PageDto<BoatsForMenuDto>> findBoatsWithPaginationSortedByField(int offset, int pageSize, String field);
    ResponseEntity<PageDto<BoatDto>> findBoatsWithPaginationSortedByFieldAndFilteredByOwner(int offset, int pageSize,
                                                                                                      String field, String token);
    ResponseEntity<BoatDto> findBoat(Long id);
    ResponseEntity<BoatDto> addBoat(AddBoatDto boatDto, String token);
    ResponseEntity<BoatDto> addBoatPhotos(Long id, List<String> photos);
}
