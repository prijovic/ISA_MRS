package rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.ReservationDtos;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ReservationIncomeDto implements Serializable {
    private Long id;
    private double fee;
    private LocalDateTime timeStamp;
    private Double value;
}
