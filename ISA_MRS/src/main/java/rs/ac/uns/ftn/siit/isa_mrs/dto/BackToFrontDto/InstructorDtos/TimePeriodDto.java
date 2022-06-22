package rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.InstructorDtos;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class TimePeriodDto implements Serializable {
    private LocalDateTime start;
    private LocalDateTime end;

    public TimePeriodDto(LocalDateTime initDate, LocalDateTime termDate) {
        this.end = termDate;
        this.start = initDate;
    }
}
