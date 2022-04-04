package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "adventure")
public class Adventure extends RentalObject {
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "adventure", cascade = CascadeType.ALL)
    private Set<AdventureEquipment> adventureEquipment = new java.util.LinkedHashSet<>();
}