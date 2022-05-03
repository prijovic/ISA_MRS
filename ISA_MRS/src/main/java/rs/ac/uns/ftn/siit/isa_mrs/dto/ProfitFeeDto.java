package rs.ac.uns.ftn.siit.isa_mrs.dto;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.FeeType;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;

import java.io.Serializable;

@Data
public class ProfitFeeDto implements Serializable {
    private double value;
    private FeeType feeType;
    private RentalObjectType rentalObjectType;
}
