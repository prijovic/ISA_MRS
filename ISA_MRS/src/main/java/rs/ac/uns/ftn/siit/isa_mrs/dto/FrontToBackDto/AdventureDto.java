package rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.dto.AdditionalServiceDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.AdventureEquipmentDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.ConductRuleDto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
public class AdventureDto implements Serializable {
    private final Long id;
    private final String name;
    private final String description;
    private final Integer capacity;
    private final Double price;
    private final Collection<AdditionalServiceDto> additionalServices;
    private final Collection<ConductRuleDto> conductRules;
    private final double cancellationFee;
    private final AddressDto address;
    private final Double duration;
    private final Collection<AdventureEquipmentDto> adventureEquipment;
    private LocalDateTime initDate;
    private LocalDateTime termDate;
}
