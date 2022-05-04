package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoyaltyCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private Integer requiredPoints;
    private Double clientDiscount;
    private Double ownerBenefit;
    private LocalDateTime lastEdited;
    private String icon;
}