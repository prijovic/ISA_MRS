package rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateUserDto implements Serializable {
    private final String email;
    private final String name;
    private final String surname;
    private final String phone;
    private final AddressDto address;
}
