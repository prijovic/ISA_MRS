package rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.InstructorDtos;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.AdminDtos.GraphDto;

import java.util.Collection;

@Data
public class DashboardInstructorDto {
    private GraphDto yearlyGraph;
    private GraphDto monthlyGraph;
    private GraphDto weeklyGraph;
    private Collection<RentalGradeDto> rentalGrades;
}
