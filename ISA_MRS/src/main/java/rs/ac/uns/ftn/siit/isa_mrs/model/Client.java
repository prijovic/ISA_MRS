package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedHashSet;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Client extends SaleParticipant{
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private Collection<Reservation> reservations = new LinkedHashSet<>();
    @ManyToMany(mappedBy = "subscribers")
    private Collection<RentalObject> subscriptions = new LinkedHashSet<>();
}
