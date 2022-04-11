package rs.ac.uns.ftn.siit.isa_mrs.dto;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.model.ConductRuleDto1;
import rs.ac.uns.ftn.siit.isa_mrs.model.PhotoDto1;
import rs.ac.uns.ftn.siit.isa_mrs.model.RentalObjectOwnerDto1;
import rs.ac.uns.ftn.siit.isa_mrs.model.RoomDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Collection;

@Data
public class VacationRentalDto implements Serializable {
    private final Long id;
    private final String name;
    private final RentalObjectType rentalObjectType;
    private final String description;
    private final Collection<PhotoDto1> photos;
    private final int capacity;
    private final double price;
    private final Collection<ConductRuleDto1> conductRules;
    private final RentalObjectOwnerDto1 rentalObjectOwner;
    private final Collection<RoomDto> rooms;
    private final LocalTime checkIn;
    private final LocalTime checkOut;
}
