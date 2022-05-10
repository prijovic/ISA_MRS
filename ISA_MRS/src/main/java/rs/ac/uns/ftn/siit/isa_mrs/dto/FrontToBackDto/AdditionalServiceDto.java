package rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AdditionalServiceDto implements Serializable {
    private String name;
    private Double price;
}
