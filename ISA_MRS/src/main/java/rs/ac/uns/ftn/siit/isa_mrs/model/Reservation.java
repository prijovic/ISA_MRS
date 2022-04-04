package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private int people;
    private boolean equipmentRequired;
    private LocalDateTime timeStamp;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "reservation", cascade = CascadeType.ALL)
    private Set<Review> reviews = new java.util.LinkedHashSet<>();
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "reservation")
    private Set<Report> reports = new java.util.LinkedHashSet<>();
    @ManyToOne
    private RentalObject rentalObject;
    @ManyToOne
    private Client client;
}