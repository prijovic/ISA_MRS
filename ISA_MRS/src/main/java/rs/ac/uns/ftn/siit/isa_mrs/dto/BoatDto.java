package rs.ac.uns.ftn.siit.isa_mrs.dto;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
public class BoatDto implements Serializable {
    private Long id;
    private String name;
    private RentalObjectType rentalObjectType;
    private String description;
    private Collection<PhotoDto> photos;
    private Integer capacity;
    private Double price;
    private AddressDto address;
    private Boolean isActive;
    private Boolean isDeletable;
    private Double cancellationFee;
    private Collection<ConductRuleDto> conductRules;
    private RentalObjectOwnerDto rentalObjectOwner;
    private String type;
    private Double length;
    private String engineNumber;
    private Double enginePower;
    private Double maxSpeed;
    private Collection<NavigationEquipmentDto> navigationEquipment;
    private Collection<FishingEquipmentDto> fishingEquipment;
    private Collection<AdditionalServiceDto> additionalServices;
    private LocalDateTime initDate;
    private LocalDateTime termDate;
}
