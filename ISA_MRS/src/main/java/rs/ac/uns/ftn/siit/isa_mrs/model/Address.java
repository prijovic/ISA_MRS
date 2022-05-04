package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String country;
    private String city;
    private String street;
    private Integer number;
    private Double latitude;
    private Double longitude;
    @OneToMany(mappedBy = "address")
    private Collection<User> users = new HashSet<>();
    @OneToMany(mappedBy = "address")
    private Collection<RentalObject> rentalObjects = new HashSet<>();
}
