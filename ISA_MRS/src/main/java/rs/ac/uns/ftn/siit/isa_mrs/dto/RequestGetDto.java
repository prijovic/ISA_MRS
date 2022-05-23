package rs.ac.uns.ftn.siit.isa_mrs.dto;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RequestType;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class RequestGetDto implements Serializable {
    private Long id;
    private RequestType type;
    private LocalDateTime timeStamp;
    private BasicUserInfoDto user;
}
