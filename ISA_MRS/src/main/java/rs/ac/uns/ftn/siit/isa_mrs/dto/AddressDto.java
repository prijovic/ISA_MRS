package rs.ac.uns.ftn.siit.isa_mrs.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddressDto implements Serializable {
    private Long id;
    private String country;
    private String city;
    private String street;
    private Integer number;
    private Double latitude;
    private Double longitude;
}
