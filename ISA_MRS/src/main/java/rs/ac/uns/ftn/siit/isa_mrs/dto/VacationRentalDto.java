package rs.ac.uns.ftn.siit.isa_mrs.dto;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;

import java.io.Serializable;
import java.util.Collection;

@Data
public class VacationRentalDto implements Serializable {
    private Long id;
    private String name;
    private RentalObjectType rentalObjectType;
    private Collection<PhotoDto> photos;
    private int capacity;
    private double price;
    private Collection<ReservationDto> reservation;
    private Collection<RoomDto> rooms;
}
