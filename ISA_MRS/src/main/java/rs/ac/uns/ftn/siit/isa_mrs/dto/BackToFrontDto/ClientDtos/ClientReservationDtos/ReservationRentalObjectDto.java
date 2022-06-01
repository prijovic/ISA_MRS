package rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.ClientDtos.ClientReservationDtos;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PhotoDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;

import java.io.Serializable;
import java.util.Collection;

@Data
public class ReservationRentalObjectDto implements Serializable {
    private Long id;
    private String name;
    private RentalObjectType rentalObjectType;
    private PhotoDto displayPhoto = null;
}
