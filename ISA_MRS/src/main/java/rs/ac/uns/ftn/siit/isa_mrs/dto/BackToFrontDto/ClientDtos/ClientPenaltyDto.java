package rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.ClientDtos;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RequestStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ClientPenaltyDto implements Serializable {
    private Long id;
    private String comment;
    private RequestStatus status;
    private Boolean showedUp;
    private LocalDateTime timeStamp;
    private ClientPenaltyReservationDto reservation;
    private ClientPenaltyAuthorDto author;
}
