package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimePeriod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private LocalDate initDate;
    private LocalDate termDate;
    @OneToOne
    private RentalObject rentalObject;
    @OneToOne
    private Reservation reservation;
    @ManyToOne
    private RentalObjectOwner rentalObjectOwner;
}