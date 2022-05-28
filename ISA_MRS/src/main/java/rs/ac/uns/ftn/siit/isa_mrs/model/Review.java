package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.*;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.ReviewType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String comment;
    private Integer grade;
    private ReviewType reviewType;
    private LocalDateTime timeStamp;
    @ManyToOne
    private Reservation reservation;
    @ManyToOne
    private User author;
}