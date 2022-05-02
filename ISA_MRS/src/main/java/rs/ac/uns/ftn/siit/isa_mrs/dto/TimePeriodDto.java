package rs.ac.uns.ftn.siit.isa_mrs.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class TimePeriodDto implements Serializable {
    private final Long id;
    private final LocalDateTime initDate;
    private final LocalDateTime termDate;
}
