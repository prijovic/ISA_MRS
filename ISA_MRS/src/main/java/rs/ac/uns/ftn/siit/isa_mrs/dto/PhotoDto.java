package rs.ac.uns.ftn.siit.isa_mrs.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PhotoDto implements Serializable {
    private Long id;
    private String photo;
}
