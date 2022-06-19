package rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.dto.ServiceDto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
public class SpecialOfferDto implements Serializable {
    private final Long rentalObjectId;
    private LocalDateTime initDate;
    private LocalDateTime termDate;
    private final Integer capacity;
    private final Double discount;
    private final Collection<ServiceDto> includedServices;
}
