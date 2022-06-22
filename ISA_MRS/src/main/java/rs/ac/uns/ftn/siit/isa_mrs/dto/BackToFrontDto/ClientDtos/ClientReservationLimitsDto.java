package rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.ClientDtos;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.dto.AdditionalServiceDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.InstructorDtos.TimePeriodDto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
public class ClientReservationLimitsDto implements Serializable {
    private LocalDateTime rentalInitDate;
    private LocalDateTime ownerInitDate;
    private LocalDateTime rentalTermDate;
    private LocalDateTime ownerTermDate;
    Collection<TimePeriodDto> reservationsPeriods;
    Collection<AdditionalServiceDto> additionalServices;
    private Double price;
}