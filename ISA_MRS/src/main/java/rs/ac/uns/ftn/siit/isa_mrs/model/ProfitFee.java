package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.Getter;
import lombok.Setter;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;

import javax.persistence.Entity;


@Entity
@Getter
@Setter
public class ProfitFee extends Fee {
    private RentalObjectType rentalObjectType;
}
