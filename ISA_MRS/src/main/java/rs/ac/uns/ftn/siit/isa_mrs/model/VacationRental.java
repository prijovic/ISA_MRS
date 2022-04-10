package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalTime;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class VacationRental extends RentalObject {
    @OneToMany(mappedBy = "vacationRental", cascade = CascadeType.ALL)
    private Collection<Room> rooms = new java.util.LinkedHashSet<>();
    private LocalTime checkIn;
    private LocalTime checkOut;
}
