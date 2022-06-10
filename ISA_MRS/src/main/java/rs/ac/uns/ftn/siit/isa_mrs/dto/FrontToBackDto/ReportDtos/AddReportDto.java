package rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.ReportDtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddReportDto implements Serializable {
    private Long reservationId;
    private String comment;
}
