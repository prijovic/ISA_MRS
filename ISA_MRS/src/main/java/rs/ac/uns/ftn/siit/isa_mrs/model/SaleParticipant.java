package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class SaleParticipant extends User {
    private Integer points;
}
