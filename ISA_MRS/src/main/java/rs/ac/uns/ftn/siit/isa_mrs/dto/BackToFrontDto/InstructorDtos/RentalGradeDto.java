package rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.InstructorDtos;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.dto.RentalObjectDto;

import java.io.Serializable;

@Data
public class RentalGradeDto implements Serializable {
    private RentalObjectDto rentalObject;
    private String grade;
}
