package rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.ClientDtos.ClientReservationDtos;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.ReservationDtos.ReservationAdditionalServiceDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.ReservationDtos.ReservationRentalObjectDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.ReservationDtos.ReservationReportDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.ReservationDtos.ReservationReviewDto;

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
    private LocalDateTime initDate;
    private LocalDateTime termDate;
    private Collection<ReservationReviewDto> reviews;
    private Collection<ReservationReportDto> reports;
    private ReservationRentalObjectDto rentalObject;
    private Collection<ReservationAdditionalServiceDto> additionalServices;
}
