package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class FishingEquipment extends Equipment{
    @ManyToOne
    @JoinColumn(name = "boat_id")
    private Boat boat;
}