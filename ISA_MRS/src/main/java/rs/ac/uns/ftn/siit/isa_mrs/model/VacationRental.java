package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalTime;
import java.util.Collection;
import java.util.LinkedHashSet;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VacationRental extends RentalObject {
    @OneToMany(mappedBy = "vacationRental", cascade = CascadeType.ALL)
    private Collection<Room> rooms = new LinkedHashSet<>();
    private LocalTime checkIn;
    private LocalTime checkOut;
}
