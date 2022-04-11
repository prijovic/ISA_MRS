package rs.ac.uns.ftn.siit.isa_mrs.dto;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.UserType;

import java.io.Serializable;

@Data
public class BasicUserInfoDto implements Serializable {
    private final Long id;
    private final String name;
    private final String surname;
    private final String phone;
    private final UserType userType;
}
