package rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.ClientDtos;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.UserType;

import java.io.Serializable;

@Data
public class ClientPenaltyAuthorDto implements Serializable {
    private Long id;
    private String name;
    private String surname;
    private UserType userType;
}
