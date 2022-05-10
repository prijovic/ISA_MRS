package rs.ac.uns.ftn.siit.isa_mrs.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RentalObjectOwnerDto implements Serializable {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private PhotoDto photo;
}
