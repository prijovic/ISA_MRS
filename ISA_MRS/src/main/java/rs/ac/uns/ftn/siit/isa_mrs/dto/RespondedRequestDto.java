package rs.ac.uns.ftn.siit.isa_mrs.dto;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RequestStatus;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RequestType;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class RespondedRequestDto implements Serializable {
    private Long id;
    private RequestStatus status;
    private RequestType type;
    private LocalDateTime timeStamp;
    private UserDto user;
    private RequestResponseDto response;
}
