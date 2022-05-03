package rs.ac.uns.ftn.siit.isa_mrs.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ReviewDto implements Serializable {
    private final Long id;
    private final String comment;
    private final int grade;
    private final LocalDateTime timeStamp;
}
