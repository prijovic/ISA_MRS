package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "adventure_equipment")
public class AdventureEquipment extends Equipment {
    @ManyToOne
    @JoinColumn(name = "adventure_id")
    private Adventure adventure;
}