package rs.ac.uns.ftn.siit.isa_mrs.dto;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.model.AdventureEquipmentDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.ConductRuleDto3;
import rs.ac.uns.ftn.siit.isa_mrs.model.PhotoDto3;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;

import java.io.Serializable;
import java.util.Collection;

@Data
public class AdventureDto implements Serializable {
    private final Long id;
    private final String name;
    private final RentalObjectType rentalObjectType;
    private final String description;
    private final Collection<PhotoDto3> photos;
    private final int capacity;
    private final double price;
    private final Collection<ConductRuleDto3> conductRules;
    private final Collection<AdventureEquipmentDto> adventureEquipment;
}
