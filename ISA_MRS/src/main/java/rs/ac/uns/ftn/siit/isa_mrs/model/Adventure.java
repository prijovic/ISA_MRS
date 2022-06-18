package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Adventure extends RentalObject {
    private Double duration;
    @OneToMany(mappedBy = "adventure", cascade = CascadeType.ALL)
    private Collection<AdventureEquipment> adventureEquipment = new java.util.LinkedHashSet<>();

    public boolean hasEquipment(String equipmentName) {
        for (Equipment equipment : adventureEquipment) {
            if (equipment.getName().equals(equipmentName)) {
                return  true;
            }
        }
        return false;
    }
}