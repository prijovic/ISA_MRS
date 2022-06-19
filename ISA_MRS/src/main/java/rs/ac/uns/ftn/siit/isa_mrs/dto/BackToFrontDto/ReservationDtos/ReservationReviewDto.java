package rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.ReservationDtos;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RequestStatus;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.ReviewType;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ReservationReviewDto implements Serializable {
    private Long id;
    private String comment;
    private Integer grade;
    private ReviewType reviewType;
    private RequestStatus status;
    private LocalDateTime timeStamp;
}
