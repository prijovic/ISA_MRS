package rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.ClientDtos.ClientReservationDtos;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.dto.AdditionalServiceDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.TimePeriodDto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
public class ClientReservationDto implements Serializable {
    private Long id;
    private Integer price;
    private Integer people;
    private Boolean equipmentRequired;
    private LocalDateTime timeStamp;
    private TimePeriodDto reservationTime;
    private Collection<ReservationReviewDto> reviews;
    private Collection<ReservationReportDto> reports;
    private ReservationRentalObjectDto rentalObject;
    private Collection<ReservationAdditionalServiceDto> additionalServices;
}
