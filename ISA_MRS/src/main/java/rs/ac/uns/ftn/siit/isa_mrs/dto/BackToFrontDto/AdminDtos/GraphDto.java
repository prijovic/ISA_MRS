package rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.AdminDtos;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Data
public class GraphDto implements Serializable {
    private Collection<GraphNodeDto> nodes = new ArrayList<>();
}
