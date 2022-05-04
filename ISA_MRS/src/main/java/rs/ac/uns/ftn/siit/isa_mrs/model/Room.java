package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private Integer beds;
    @ManyToOne
    private VacationRental vacationRental;
}