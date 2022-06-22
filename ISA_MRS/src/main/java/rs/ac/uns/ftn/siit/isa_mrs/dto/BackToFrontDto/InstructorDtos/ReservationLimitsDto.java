package rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.InstructorDtos;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.dto.ClientDto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
public class ReservationLimitsDto implements Serializable {
    Collection<ClientDto> clients;
    private LocalDateTime rentalInitDate;
    private LocalDateTime ownerInitDate;
    private LocalDateTime rentalTermDate;
    private LocalDateTime ownerTermDate;
    Collection<TimePeriodDto> reservationsPeriods;
}
