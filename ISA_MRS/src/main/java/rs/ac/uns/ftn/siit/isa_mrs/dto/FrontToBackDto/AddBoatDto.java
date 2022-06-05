package rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FishingEquipmentDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.NavigationEquipmentDto;

import java.io.Serializable;
import java.util.Collection;

@Data
public class AddBoatDto implements Serializable {
    private String name;
    private String boatOwnerEmail;
    private String description;
    private Integer capacity;
    private Double price;
    private Collection<AdditionalServiceDto> additionalServices;
    private Collection<ConductRuleDto> conductRules;
    private CancellationFeeDto cancellationFee;
    private AddressDto address;
    private String type;
    private Double length;
    private String engineNumber;
    private Double enginePower;
    private Double maxSpeed;
    private Collection<NavigationEquipmentDto> navigationEquipment;
    private Collection<FishingEquipmentDto> fishingEquipment;
}
