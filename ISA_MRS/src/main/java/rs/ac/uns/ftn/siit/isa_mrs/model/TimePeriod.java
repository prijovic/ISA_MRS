package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "time_period")
public class TimePeriod {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private LocalDateTime initDate;
    private LocalDateTime termDate;
    @ManyToOne
    @JoinColumn(name = "rental_object_id")
    private RentalObject rentalObject;
}