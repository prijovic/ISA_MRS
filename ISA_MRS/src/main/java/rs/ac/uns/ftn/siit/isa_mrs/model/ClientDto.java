package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.UserType;

import java.io.Serializable;

@Data
public class ClientDto implements Serializable {
    private final Long id;
    private final String photo;
    private final String name;
    private final String surname;
    private final UserType userType;
}
