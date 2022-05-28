package rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.VacationRentalDtos;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.dto.AddressDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PhotoDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.RoomDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;

import java.io.Serializable;
import java.util.Collection;

@Data
public class VacationRentalsForMenuDto implements Serializable {
    private Long id;
    private String name;
    private RentalObjectType rentalObjectType;
    private Integer capacity;
    private Double price;
    private AddressDto address;
    private Collection<RoomDto> rooms;
    private PhotoDto displayPhoto = null;
    private String grade = null;
}
