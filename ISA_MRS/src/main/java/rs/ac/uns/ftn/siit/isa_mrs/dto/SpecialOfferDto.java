package rs.ac.uns.ftn.siit.isa_mrs.dto;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.dto.ServiceDto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
public class SpecialOfferDto implements Serializable {
    private Long id;
    private LocalDateTime initDate;
    private LocalDateTime termDate;
    private Integer capacity;
    private Double discount;
    private Collection<ServiceDto> includedServices;
}
