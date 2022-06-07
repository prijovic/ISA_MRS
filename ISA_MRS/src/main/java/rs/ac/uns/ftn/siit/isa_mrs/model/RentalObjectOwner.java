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
@NoArgsConstructor
@AllArgsConstructor
public class RentalObjectOwner extends SaleParticipant{
    @OneToMany(mappedBy = "rentalObjectOwner")
    private Collection<TimePeriod> unavailabilityPeriods = new LinkedHashSet<>();
    @OneToMany(mappedBy = "rentalObjectOwner", cascade = CascadeType.ALL)
    private Collection<RentalObject> rentalObjects = new LinkedHashSet<>();
}
