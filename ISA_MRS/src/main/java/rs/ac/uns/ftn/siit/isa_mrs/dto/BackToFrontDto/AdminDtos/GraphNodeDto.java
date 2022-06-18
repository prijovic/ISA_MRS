package rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.AdminDtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class GraphNodeDto implements Serializable {
    private String month;
    private double value;
}
