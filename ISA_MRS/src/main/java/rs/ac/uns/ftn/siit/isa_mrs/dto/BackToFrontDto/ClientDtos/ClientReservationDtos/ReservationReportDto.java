package rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.ClientDtos.ClientReservationDtos;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ReservationReportDto implements Serializable {
    private Long id;
    private String comment;
}
