package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedHashSet;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class RentalObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private RentalObjectType rentalObjectType;
    private String description;
    @OneToMany(mappedBy = "rentalObject", cascade = CascadeType.ALL)
    private Collection<Photo> photos = new LinkedHashSet<>();
    private Integer capacity;
    private Double price;
    @OneToMany(mappedBy = "rentalObject", cascade = CascadeType.ALL)
    private Collection<AdditionalService> additionalServices = new LinkedHashSet<>();
    @OneToMany(mappedBy = "rentalObject", cascade =  CascadeType.ALL)
    private Collection<ConductRule> conductRules = new LinkedHashSet<>();
    @OneToMany(mappedBy = "rentalObject", cascade = CascadeType.ALL)
    private Collection<SpecialOffer> specialOffers = new LinkedHashSet<>();
    @OneToOne(cascade = CascadeType.ALL)
    private CancellationFee cancellationFee;
    @OneToMany(mappedBy = "rentalObject", cascade = CascadeType.ALL)
    private Collection<TimePeriod> availabilityPeriods = new LinkedHashSet<>();
    @OneToMany(mappedBy = "rentalObject", cascade = CascadeType.ALL)
    private Collection<Reservation> reservations = new LinkedHashSet<>();
    @ManyToOne
    private RentalObjectOwner rentalObjectOwner;
    @ManyToOne
    private Address address;
    @ManyToMany
    @JoinTable(name = "subscribed_client",
            joinColumns = @JoinColumn(name = "subscriber_id"),
            inverseJoinColumns = @JoinColumn(name = "rental_id"))
    private Collection<Client> subscribers = new LinkedHashSet<>();
}
