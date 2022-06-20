package rs.ac.uns.ftn.siit.isa_mrs.dto;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
public class VacationRentalDto implements Serializable {
    private Long id;
    private String name;
    private RentalObjectType rentalObjectType;
    private String description;
    private Collection<PhotoDto> photos;
    private AddressDto address;
    private Integer capacity;
    private Double price;
    private Boolean isActive;
    private Boolean isDeletable;
    private Double cancellationFee;
    private RentalObjectOwnerDto rentalObjectOwner;
    private Collection<ConductRuleDto> conductRules;
    private Collection<AdditionalServiceDto> additionalServices;
    private Collection<RoomDto> rooms;
    private LocalDateTime initDate;
    private LocalDateTime termDate;
}
