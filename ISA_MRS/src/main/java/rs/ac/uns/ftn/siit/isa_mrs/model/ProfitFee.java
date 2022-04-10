package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.*;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;

import javax.persistence.Entity;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfitFee extends Fee {
    private RentalObjectType rentalObjectType;
}
