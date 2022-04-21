package rs.ac.uns.ftn.siit.isa_mrs.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class RequestResponseDto implements Serializable {
    private Long id;
    private LocalDateTime timeStamp;
    private String comment;
    private String operatorEmail;
}
