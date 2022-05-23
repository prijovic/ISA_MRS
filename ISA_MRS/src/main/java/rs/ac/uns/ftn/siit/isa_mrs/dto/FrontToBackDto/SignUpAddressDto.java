package rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SignUpAddressDto implements Serializable {
    private String country;
    private String city;
    private String street;
    private Integer number;
    private Double latitude;
    private Double longitude;
}
