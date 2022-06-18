package rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.ReservationDtos;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.UserType;

import java.io.Serializable;

@Data
public class ReservationReportDto implements Serializable {
    private Long id;
    private String comment;
    private Boolean showedUp;
    private UserType user;
}
