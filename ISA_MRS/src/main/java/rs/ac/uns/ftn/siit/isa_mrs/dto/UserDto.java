package rs.ac.uns.ftn.siit.isa_mrs.dto;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.UserType;

import java.io.Serializable;

@Data
public class UserDto implements Serializable {
    private final Long id;
    private final String photo;
    private final String email;
    private final String name;
    private final String surname;
    private final String phone;
    private final boolean isActive;
    private final UserType userType;
    private final AddressDto address;
}
