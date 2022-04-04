package rs.ac.uns.ftn.siit.isa_mrs.model;

import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.FeeType;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "fee")
public class Fee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private FeeType feeType;
    private double value;
}