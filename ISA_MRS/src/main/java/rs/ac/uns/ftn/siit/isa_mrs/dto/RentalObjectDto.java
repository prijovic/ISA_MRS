package rs.ac.uns.ftn.siit.isa_mrs.dto;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.model.Photo;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;

import java.io.Serializable;
import java.util.Collection;

@Data
public class RentalObjectDto implements Serializable {
    private Long id;
    private String name;
    private RentalObjectType rentalObjectType;
    private Boolean isActive;
    private Collection<PhotoDto> photos;
    private AddressDto address;
    private String description;
    private Double price;
    private RentalObjectOwnerDto rentalObjectOwner;
}
