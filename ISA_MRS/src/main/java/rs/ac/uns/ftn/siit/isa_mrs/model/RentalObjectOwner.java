package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.Getter;
import lombok.Setter;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectOwnerType;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedHashSet;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Setter
@Getter
public class RentalObjectOwner extends SaleParticipant{
    private RentalObjectOwnerType ownerType;
    @OneToMany(mappedBy = "rentalObjectOwner", cascade = CascadeType.ALL)
    private Collection<RentalObject> rentalObjects = new LinkedHashSet<>();
}
