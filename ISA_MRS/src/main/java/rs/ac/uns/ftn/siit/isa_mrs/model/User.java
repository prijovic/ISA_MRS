package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.Getter;
import lombok.Setter;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.UserType;

import javax.persistence.*;

@Table(name = "users")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Setter
@Getter
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String photo;
    private String email;
    private String password;
    private String name;
    private String surname;
    private String phone;
    private boolean isActive;
    private UserType userType;
    @ManyToOne
    private Address address;
    @OneToOne(cascade = CascadeType.ALL)
    private Request request;

}
