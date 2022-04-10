package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Setter
@Getter
@Table(name = "client")
public class Client extends SaleParticipant{
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private Collection<Reservation> reservations;
}
