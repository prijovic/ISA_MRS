package rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.VacationRentalOwnerDtos;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.InstructorDtos.TimePeriodDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.ClientDto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
public class ReservationRentalLimitsDto implements Serializable {
    Collection<ClientDto> clients;
    private LocalDateTime initDate;
    private LocalDateTime termDate;
    Collection<TimePeriodDto> reservationsPeriods;
}
