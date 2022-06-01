package rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.ConductType;

import java.io.Serializable;

@Data
public class ConductRuleDto implements Serializable {
    private String type;
    private String rule;
}
