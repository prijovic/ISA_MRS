package rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.InstructorDtos.InstructorMenu;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.InstructorDtos.InstructorAddressDto;

import java.io.Serializable;

@Data
public class InstructorsForMenuDto implements Serializable {
    private Long id;
    private String photo;
    private String name;
    private String surname;
    private Boolean isActive;
    private InstructorAddressDto address;
    private String grade = null;
}
