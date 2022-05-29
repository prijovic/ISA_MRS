package rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.BoatDtos;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.dto.AddressDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PhotoDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;

import java.io.Serializable;

@Data
public class BoatsForMenuDto implements Serializable {
    private Long id;
    private String name;
    private RentalObjectType rentalObjectType;
    private Integer capacity;
    private Double price;
    private String type;
    private Double maxSpeed;
    private AddressDto address;
    private PhotoDto displayPhoto = null;
    private String grade = null;
}
