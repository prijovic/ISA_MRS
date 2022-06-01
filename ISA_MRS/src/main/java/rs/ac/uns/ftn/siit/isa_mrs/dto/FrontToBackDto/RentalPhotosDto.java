package rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RentalPhotosDto implements Serializable {
    private Long id;
    private List<String> photos;
}
