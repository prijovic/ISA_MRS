package rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddressDto implements Serializable {
    private final String country;
    private final String city;
    private final String street;
    private final Integer number;
    private final Double latitude;
    private final Double longitude;
}
