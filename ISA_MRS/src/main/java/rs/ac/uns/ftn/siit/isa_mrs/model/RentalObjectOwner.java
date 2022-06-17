package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedHashSet;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RentalObjectOwner extends SaleParticipant{
    private LocalDateTime initDate;
    private LocalDateTime termDate;
    @OneToMany(mappedBy = "rentalObjectOwner", cascade = CascadeType.ALL)
    private Collection<RentalObject> rentalObjects = new LinkedHashSet<>();
}
