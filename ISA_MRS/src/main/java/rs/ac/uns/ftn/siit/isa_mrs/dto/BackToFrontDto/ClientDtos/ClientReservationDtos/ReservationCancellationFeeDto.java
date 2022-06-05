package rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.ClientDtos.ClientReservationDtos;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.FeeType;

import java.io.Serializable;

@Data
public class ReservationCancellationFeeDto implements Serializable {
    private final Long id;
    private final FeeType feeType;
    private final Double value;
}
