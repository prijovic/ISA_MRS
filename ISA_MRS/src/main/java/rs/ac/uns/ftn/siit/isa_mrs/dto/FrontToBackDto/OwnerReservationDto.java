package rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class OwnerReservationDto implements Serializable {
    private long clientId;
    private long rentalId;
    private final LocalDateTime initDate;
    private final LocalDateTime termDate;
    private boolean isEquipmentNeeded;
}
