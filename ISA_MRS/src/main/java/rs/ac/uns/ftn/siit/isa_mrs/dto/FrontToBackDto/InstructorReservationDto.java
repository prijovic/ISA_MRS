package rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class InstructorReservationDto implements Serializable {
    private long clientId;
    private long rentalId;
    private LocalDateTime initDate;
    private boolean isEquipmentNeeded;
}
