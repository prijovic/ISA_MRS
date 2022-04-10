package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.Getter;
import lombok.Setter;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedHashSet;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Setter
@Getter
public class RentalObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private RentalObjectType rentalObjectType;
    private String description;
    @OneToMany(mappedBy = "rentalObject", cascade = CascadeType.ALL)
    private Collection<Photo> photos = new java.util.LinkedHashSet<>();
    private int capacity;
    private double price;
    @OneToMany(mappedBy = "rentalObject", cascade = CascadeType.ALL)
    private Collection<AdditionalService> additionalServices = new LinkedHashSet<>();
    @OneToMany(mappedBy = "rentalObject", cascade =  CascadeType.ALL)
    private Collection<ConductRule> conductRules = new LinkedHashSet<>();
    @OneToMany(mappedBy = "rentalObject", cascade = CascadeType.ALL)
    private Collection<SpecialOffer> specialOffers = new LinkedHashSet<>();
    @ManyToOne
    private CancellationFee cancellationFee;
    @OneToMany(mappedBy = "rentalObject", cascade = CascadeType.ALL)
    private Collection<TimePeriod> availabilityPeriods = new LinkedHashSet<>();
    @OneToMany(mappedBy = "rentalObject", cascade = CascadeType.ALL)
    private Collection<Reservation> reservation = new LinkedHashSet<>();
    @ManyToOne
    private RentalObjectOwner rentalObjectOwner;
}
