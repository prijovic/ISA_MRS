package rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.AdminDtos;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class IncomeDto implements Serializable {
    private Long id;
    private double value;
    private LocalDateTime timeStamp;
    private String type;
}
