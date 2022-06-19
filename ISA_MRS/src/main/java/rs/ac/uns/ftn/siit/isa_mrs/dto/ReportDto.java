package rs.ac.uns.ftn.siit.isa_mrs.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ReportDto implements Serializable {
    private final Long id;
    private final String comment;
    private final LocalDateTime timeStamp;
}
