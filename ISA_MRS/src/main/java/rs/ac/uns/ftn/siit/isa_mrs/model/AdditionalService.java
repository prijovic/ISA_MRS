package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedHashSet;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdditionalService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private Double price;
    @ManyToOne
    private RentalObject rentalObject;
    @ManyToMany
    @JoinTable(name = "reservation_additional_services",
            joinColumns = @JoinColumn(name = "additional_service_id"),
            inverseJoinColumns = @JoinColumn(name = "reservation_id"))
    private Collection<Reservation> reservation = new LinkedHashSet<>();
//    @ManyToOne
//    private Reservation reservation;
}