package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "admin")
public class Admin extends User {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "operator", cascade = CascadeType.ALL)
    private Set<Response> responses = new LinkedHashSet<>();
}