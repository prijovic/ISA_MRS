package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.Getter;
import lombok.Setter;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Setter
@Getter
public abstract class RentalObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private RentalObjectType rentalObjectType;
    private String description;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "rentalObject", cascade = CascadeType.ALL)
    private Set<Photo> photos = new java.util.LinkedHashSet<>();
    private int capacity;
    private double price;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "rentalObject", cascade = CascadeType.ALL)
    private Set<AdditionalService> additionalServices = new LinkedHashSet<>();
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "rentalObject", cascade =  CascadeType.ALL)
    private Set<ConductRule> conductRules = new LinkedHashSet<>();
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "rentalObject", cascade = CascadeType.ALL)
    private Set<SpecialOffer> specialOffers = new LinkedHashSet<>();
    @ManyToOne
    private CancellationFee cancellationFee;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "rentalObject", cascade = CascadeType.ALL)
    private Set<TimePeriod> availabilityPeriods = new LinkedHashSet<>();
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "rentalObject", cascade = CascadeType.ALL)
    private Set<Reservation> reservation = new LinkedHashSet<>();
    @ManyToOne
    private RentalObjectOwner rentalObjectOwner;
}
