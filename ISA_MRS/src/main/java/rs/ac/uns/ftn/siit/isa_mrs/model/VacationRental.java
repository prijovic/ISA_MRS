package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalTime;
import java.util.Set;

@Setter
@Getter
@Entity
public class VacationRental extends RentalObject {
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "vacationRental", cascade = CascadeType.ALL)
    private Set<Room> rooms = new java.util.LinkedHashSet<>();
    private LocalTime checkIn;
    private LocalTime checkOut;
}
