package rs.ac.uns.ftn.siit.isa_mrs.service;

import org.springframework.http.ResponseEntity;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.BoatDtos.BoatProfileDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.BoatDtos.BoatsForMenuDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BoatDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.AddBoatDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.Boat;

import java.util.Optional;

public interface BoatService {
    ResponseEntity<BoatProfileDto> getBoat(Long id, String email);
    ResponseEntity<PageDto<BoatsForMenuDto>> findBoatsWithPaginationSortedByField(int offset, int pageSize, String field);
    ResponseEntity<BoatDto> addNewBoat(AddBoatDto boat);
}
