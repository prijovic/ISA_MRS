package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FishingEquipment extends Equipment{
    @ManyToOne
    @JoinColumn(name = "boat_id")
    private Boat boat;
}