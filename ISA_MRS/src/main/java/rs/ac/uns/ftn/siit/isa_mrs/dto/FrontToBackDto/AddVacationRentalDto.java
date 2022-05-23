package rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;

import java.io.Serializable;
import java.util.Collection;

@Data
public class AddVacationRentalDto implements Serializable {
    private String name;
    private String ownerEmail;
    private String description;
    private Collection<PhotoDto> photos;
    private Integer capacity;
    private Double price;
    private Collection<AdditionalServiceDto> additionalServices;
    private Collection<ConductRuleDto> conductRules;
    private CancellationFeeDto cancellationFee;
    private AddressDto address;
    private Collection<RoomDto> rooms;
}
