package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "special_offer")
public class SpecialOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private LocalDateTime initDate;
    private LocalDateTime termDate;
    private int capacity;
    private double discount;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "specialOffer")
    private Collection<Service> includedServices = new java.util.LinkedHashSet<>();
    @ManyToOne
    @JoinColumn(name = "rental_object_id")
    private RentalObject rentalObject;
}