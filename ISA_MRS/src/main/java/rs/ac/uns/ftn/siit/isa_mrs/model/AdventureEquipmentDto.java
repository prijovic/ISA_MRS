package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class AdventureEquipmentDto implements Serializable {
    private final Long id;
    private final String name;
}
