package rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FishingEquipmentDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.NavigationEquipmentDto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
public class AddBoatDto implements Serializable {
    private final Long id;
    private final String name;
    private final String description;
    private final Integer capacity;
    private final Double price;
    private final Collection<AdditionalServiceDto> additionalServices;
    private final Collection<ConductRuleDto> conductRules;
    private final double cancellationFee;
    private final AddressDto address;
    private final String type;
    private final Double length;
    private final String engineNumber;
    private final Double enginePower;
    private final Double maxSpeed;
    private final Collection<NavigationEquipmentDto> navigationEquipment;
    private final Collection<FishingEquipmentDto> fishingEquipment;
    private LocalDateTime initDate;
    private LocalDateTime termDate;
}
