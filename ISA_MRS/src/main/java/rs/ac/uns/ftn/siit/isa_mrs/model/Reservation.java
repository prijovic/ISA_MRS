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
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private Integer price;
    private Integer people;
    private Boolean cancelled;
    private Boolean equipmentRequired;
    private LocalDateTime timeStamp;
    private LocalDateTime initDate;
    private LocalDateTime termDate;
    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
    private Collection<Review> reviews = new LinkedHashSet<>();
    @OneToMany(mappedBy = "reservation")
    private Collection<Report> reports = new LinkedHashSet<>();
    @ManyToOne
    private RentalObject rentalObject;
    @ManyToOne
    private Client client;
    @ManyToMany(mappedBy = "reservation")
    private Collection<AdditionalService> additionalServices = new LinkedHashSet<>();
    @OneToOne
    private Income income;
}