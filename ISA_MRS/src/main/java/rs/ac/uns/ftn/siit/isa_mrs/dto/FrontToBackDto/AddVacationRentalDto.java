package rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.dto.RoomDto;

import java.io.Serializable;
import java.util.Collection;

@Data
public class AddVacationRentalDto implements Serializable {
    private String name;
    private String description;
    private Integer capacity;
    private Double price;
    private Collection<AdditionalServiceDto> additionalServices;
    private Collection<ConductRuleDto> conductRules;
    private CancellationFeeDto cancellationFee;
    private AddressDto address;
    private Collection<RoomDto> rooms;
}
