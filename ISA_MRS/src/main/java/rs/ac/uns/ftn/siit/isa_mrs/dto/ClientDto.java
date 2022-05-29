package rs.ac.uns.ftn.siit.isa_mrs.dto;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.UserType;

import java.io.Serializable;

@Data
public class ClientDto implements Serializable {
    private Long id;
    private String photo;
    private String name;
    private String surname;
    private String email;
    private UserType userType;
}
