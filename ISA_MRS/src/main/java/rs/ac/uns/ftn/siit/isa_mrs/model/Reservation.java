package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private int people;
    private boolean equipmentRequired;
    private LocalDateTime timeStamp;
    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
    private Collection<Review> reviews = new java.util.LinkedHashSet<>();
    @OneToMany(mappedBy = "reservation")
    private Collection<Report> reports = new java.util.LinkedHashSet<>();
    @ManyToOne
    private RentalObject rentalObject;
    @ManyToOne
    private Client client;
}