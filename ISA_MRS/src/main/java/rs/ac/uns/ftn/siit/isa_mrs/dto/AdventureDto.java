package rs.ac.uns.ftn.siit.isa_mrs.dto;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.model.AdditionalService;
import rs.ac.uns.ftn.siit.isa_mrs.model.RentalObjectOwner;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
public class AdventureDto implements Serializable {
    private Long id;
    private String name;
    private RentalObjectType rentalObjectType;
    private String description;
    private Double duration;
    private Collection<PhotoDto> photos;
    private Integer capacity;
    private Double price;
    private AddressDto address;
    private Boolean isActive;
    private Boolean isDeletable;
    private RentalObjectOwnerDto rentalObjectOwner;
    private Collection<ConductRuleDto> conductRules;
    private Collection<AdventureEquipmentDto> adventureEquipment;
    private Collection<AdditionalServiceDto> additionalServices;
    private LocalDateTime initDate;
    private LocalDateTime termDate;
}
