package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdventureEquipment extends Equipment {
    @ManyToOne
    private Adventure adventure;
}