package rs.ac.uns.ftn.siit.isa_mrs.dto;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.UserType;

import java.io.Serializable;

@Data
public class BasicUserInfoDto implements Serializable {
    private Long id;
    private String name;
    private String surname;
    private String phone;
    private UserType userType;
}
