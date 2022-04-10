package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.*;
import org.springframework.lang.Nullable;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.UserType;

import javax.persistence.*;

@Table(name = "users")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
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
    @OneToOne
    private Request request;

}
