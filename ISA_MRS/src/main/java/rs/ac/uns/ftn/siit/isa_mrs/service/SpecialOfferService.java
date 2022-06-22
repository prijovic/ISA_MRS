package rs.ac.uns.ftn.siit.isa_mrs.service;

import org.springframework.http.ResponseEntity;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.SpecialOfferDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.SpecialOfferRentalDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.RentalObjectDto;

public interface SpecialOfferService {
    ResponseEntity<RentalObjectDto> addSpecialOffer(SpecialOfferDto dto);
    ResponseEntity<RentalObjectDto> addSpecialOfferVacationRental(SpecialOfferRentalDto dto);
    ResponseEntity<RentalObjectDto> addSpecialOfferBoat(SpecialOfferRentalDto dto);
}
