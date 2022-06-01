package rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.FeeType;

import java.io.Serializable;

@Data
public class CancellationFeeDto implements Serializable {
    private FeeType feeType;
    private Double value;
}
