package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedHashSet;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Boat extends RentalObject {
    private String type;
    private Double length;
    private String engineNumber;
    private Double enginePower;
    private Double maxSpeed;
    @OneToMany(mappedBy = "boat", cascade = CascadeType.ALL)
    private Collection<NavigationEquipment> navigationEquipment = new LinkedHashSet<>();
    @OneToMany(mappedBy = "boat", cascade = CascadeType.ALL)
    private Collection<FishingEquipment> fishingEquipment = new LinkedHashSet<>();

    public boolean hasFishingEquipment(String fishingEquipmentName) {
        for (Equipment fishingEquipment : fishingEquipment) {
            if (fishingEquipment.getName().equals(fishingEquipmentName)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasNavigationEquipment(String navigationEquipmentName) {
        for (Equipment navigationEquipment : navigationEquipment) {
            if (navigationEquipment.getName().equals(navigationEquipmentName)) {
                return true;
            }
        }
        return false;
    }
}
