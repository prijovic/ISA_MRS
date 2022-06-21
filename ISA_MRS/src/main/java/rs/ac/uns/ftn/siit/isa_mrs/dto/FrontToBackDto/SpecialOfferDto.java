package rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.ServiceDto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
public class SpecialOfferDto implements Serializable {
    private long rentalId;
    private LocalDateTime initDate;
    private int capacity;
    private double discount;
    private Collection<ServiceDto> includedServices;
}
