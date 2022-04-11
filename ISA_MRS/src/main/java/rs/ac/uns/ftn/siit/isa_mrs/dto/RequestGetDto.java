package rs.ac.uns.ftn.siit.isa_mrs.dto;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RequestType;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class RequestGetDto implements Serializable {
    private final Long id;
    private final RequestType type;
    private final LocalDateTime timeStamp;
    private final BasicUserInfoDto user;
}
