package rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.ClientDtos;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.ClientDtos.ClientReservationDtos.ClientReservationDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.AddressDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.RentalObjectDto;

import java.io.Serializable;
import java.util.Collection;

@Data
public class ClientProfileDto implements Serializable {
    private Long id;
    private String photo;
    private String email;
    private String name;
    private String surname;
    private String phone;
    private Boolean isActive;
    private AddressDto address;
    private Integer points;
    private Collection<ClientReservationDto> reservations;
    private Collection<RentalObjectDto> subscriptions;
}
