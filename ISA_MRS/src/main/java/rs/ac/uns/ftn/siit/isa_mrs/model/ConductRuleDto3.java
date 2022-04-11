package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.ConductType;

import java.io.Serializable;

@Data
public class ConductRuleDto3 implements Serializable {
    private final Long id;
    private final ConductType type;
    private final String rule;
}
