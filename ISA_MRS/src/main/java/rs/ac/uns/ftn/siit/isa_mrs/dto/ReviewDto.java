package rs.ac.uns.ftn.siit.isa_mrs.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ReviewDto implements Serializable {
    private Long id;
    private String comment;
    private Integer grade;
    private LocalDateTime timeStamp;
}
