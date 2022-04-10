package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
@Table(name = "adventure")
public class Adventure extends RentalObject {
    @OneToMany(mappedBy = "adventure", cascade = CascadeType.ALL)
    private Collection<AdventureEquipment> adventureEquipment = new java.util.LinkedHashSet<>();
}