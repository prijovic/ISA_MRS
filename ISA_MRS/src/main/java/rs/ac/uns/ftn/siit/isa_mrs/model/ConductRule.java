package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.*;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.ConductType;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConductRule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private ConductType type;
    private String rule;
    @ManyToOne
    private RentalObject rentalObject;
}