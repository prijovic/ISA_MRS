package rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.ClientDtos.ClientReservationDtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReservationCancellationFeeDto implements Serializable {
    private Long id;
    private Double value;
}
