package rs.ac.uns.ftn.siit.isa_mrs.dto;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.model.AdditionalService;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;

import java.io.Serializable;
import java.util.Collection;

@Data
public class AdventureDto implements Serializable {
    private final Long id;
    private final String name;
    private final RentalObjectType rentalObjectType;
    private final String description;
    private final Collection<PhotoDto> photos;
    private final int capacity;
    private final double price;
    private final Collection<ConductRuleDto> conductRules;
    private final Collection<AdventureEquipmentDto> adventureEquipment;
    private final Collection<AdditionalServiceDto> additionalServices;
}
