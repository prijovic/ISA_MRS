package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String comment;
    private LocalDateTime timeStamp;
    @ManyToOne
    private Reservation reservation;
    @ManyToOne
    private User author;
    @OneToOne(cascade = CascadeType.ALL)
    private ReportResponse response;
}