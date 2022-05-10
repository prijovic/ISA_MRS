package rs.ac.uns.ftn.siit.isa_mrs.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoomDto implements Serializable {
    private Long id;
    private Integer beds;
}
