package rs.ac.uns.ftn.siit.isa_mrs.dto;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;

import java.io.Serializable;

@Data
public class RentalObjectDto implements Serializable {
    private Long id;
    private String name;
    private RentalObjectType rentalObjectType;
    private RentalObjectOwnerDto rentalObjectOwner;
}
