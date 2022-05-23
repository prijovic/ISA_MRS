package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedHashSet;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpecialOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private LocalDateTime initDate;
    private LocalDateTime termDate;
    private Integer capacity;
    private Double discount;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "specialOffer")
    private Collection<Service> includedServices = new LinkedHashSet<>();
    @ManyToOne
    private RentalObject rentalObject;
}