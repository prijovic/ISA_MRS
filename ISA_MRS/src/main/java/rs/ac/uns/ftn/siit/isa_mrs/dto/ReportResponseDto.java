package rs.ac.uns.ftn.siit.isa_mrs.dto;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RequestStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ReportResponseDto implements Serializable {
    private final Long id;
    private final LocalDateTime timeStamp;
    private final String comment;
    private final RequestStatus penalty;
}
