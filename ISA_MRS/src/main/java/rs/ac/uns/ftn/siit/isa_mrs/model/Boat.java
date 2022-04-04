package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "boat")
public class Boat extends RentalObject {
    private String type;
    private double length;
    private String engineNumber;
    private double enginePower;
    private double maxSpeed;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "boat", cascade = CascadeType.ALL)
    private Set<NavigationEquipment> navigationEquipment = new java.util.LinkedHashSet<>();
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "boat", cascade = CascadeType.ALL)
    private Set<FishingEquipment> fishingEquipment = new java.util.LinkedHashSet<>();
}
