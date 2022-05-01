package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Table(name = "address")
@Entity
@Setter
@Getter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String country;
    private String city;
    private String street;
    private int number;
    private double latitude;
    private double longitude;
}
