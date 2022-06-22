package rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.ReservationDtos;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.dto.AdditionalServiceDto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
public class ClientBookDto implements Serializable {
    private Long rentalId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean isEquipmentNeeded;
    private Collection<AdditionalServiceDto> additionalServices;
    private Double total;
}
