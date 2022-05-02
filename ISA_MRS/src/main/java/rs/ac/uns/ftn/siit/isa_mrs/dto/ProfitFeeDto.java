package rs.ac.uns.ftn.siit.isa_mrs.dto;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.FeeType;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;

import java.io.Serializable;

@Data
public class ProfitFeeDto implements Serializable {
    private final double value;
    private final FeeType feeType;
    private final RentalObjectType rentalObjectType;
}
