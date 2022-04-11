package rs.ac.uns.ftn.siit.isa_mrs.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RequestType;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class RequestDto implements Serializable {
    private Long id;
    private RequestType type;
    private LocalDateTime timeStamp;
    private RequestUserDto user;
}
