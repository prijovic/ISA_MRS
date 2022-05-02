package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.*;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.UniqueConstraint;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfitFee extends Fee {
    @Column(unique = true)
    private RentalObjectType rentalObjectType;
}
