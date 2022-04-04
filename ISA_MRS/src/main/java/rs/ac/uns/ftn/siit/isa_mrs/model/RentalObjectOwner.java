package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.Getter;
import lombok.Setter;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectOwnerType;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Setter
@Getter
public class RentalObjectOwner extends SaleParticipant{
    private RentalObjectOwnerType ownerType;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "rentalObjectOwner", cascade = CascadeType.ALL)
    private Set<RentalObject> rentalObjects = new LinkedHashSet<>();
}
