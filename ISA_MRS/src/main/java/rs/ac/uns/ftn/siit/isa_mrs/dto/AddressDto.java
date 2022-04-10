package rs.ac.uns.ftn.siit.isa_mrs.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddressDto implements Serializable {
    private final Long id;
    private final String name;
}
