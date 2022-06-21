package rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.ClientDtos;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ClientPenaltyReservationDto implements Serializable {
    private Long id;
    private LocalDateTime initDate;
    private LocalDateTime termDate;
}
