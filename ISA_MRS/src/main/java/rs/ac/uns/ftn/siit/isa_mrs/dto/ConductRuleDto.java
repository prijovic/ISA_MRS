package rs.ac.uns.ftn.siit.isa_mrs.dto;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.ConductType;

import java.io.Serializable;

@Data
public class ConductRuleDto implements Serializable {
    private Long id;
    private ConductType type;
    private String rule;
}
