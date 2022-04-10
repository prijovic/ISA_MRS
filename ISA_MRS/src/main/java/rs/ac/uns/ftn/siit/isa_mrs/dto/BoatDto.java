package rs.ac.uns.ftn.siit.isa_mrs.dto;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.model.*;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;

import java.io.Serializable;
import java.util.Collection;

@Data
public class BoatDto implements Serializable {
    private final Long id;
    private final String name;
    private final RentalObjectType rentalObjectType;
    private final String description;
    private final Collection<PhotoDto2> photos;
    private final int capacity;
    private final double price;
    private final Collection<ConductRuleDto2> conductRules;
    private final RentalObjectOwnerDto2 rentalObjectOwner;
    private final String type;
    private final double length;
    private final String engineNumber;
    private final double enginePower;
    private final double maxSpeed;
    private final Collection<NavigationEquipmentDto> navigationEquipment;
    private final Collection<FishingEquipmentDto> fishingEquipment;
}
