package rs.ac.uns.ftn.siit.isa_mrs.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AdditionalServiceDto implements Serializable {
    private Long id;
    private String name;
    private Double price;
}
