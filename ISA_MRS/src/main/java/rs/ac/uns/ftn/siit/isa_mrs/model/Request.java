package rs.ac.uns.ftn.siit.isa_mrs.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RequestStatus;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RequestType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "request")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private RequestStatus status;
    private RequestType type;
    @JsonFormat(pattern="dd.MM.yyyy. HH:mm:ss")
    private LocalDateTime timeStamp;
    private String reason;
    @OneToOne
    private User user;
    @OneToOne
    private RequestResponse response;
}