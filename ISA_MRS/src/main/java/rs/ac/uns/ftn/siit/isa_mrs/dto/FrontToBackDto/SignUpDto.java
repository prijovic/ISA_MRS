package rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.UserType;

import java.io.Serializable;

@Data
public class SignUpDto implements Serializable {
    private UserType userType;
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private String photo;
    private String reason;
    private SignUpAddressDto address;
}
