package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoyaltyProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private Long clientPointsPerReservation;
    private Long ownerPointsPerReservation;
    @OneToMany(mappedBy = "loyaltyProgram", cascade = CascadeType.ALL)
    private Collection<LoyaltyCategory> loyaltyCategories = new LinkedHashSet<>();

    public LoyaltyCategory getUserCategory(int points) {
        Set<LoyaltyCategory> passedCategories = new HashSet<>();
        for (LoyaltyCategory category : loyaltyCategories) {
            if (category.getRequiredPoints() < points) {
                passedCategories.add(category);
            }
        }
        int currentMax = 0;
        LoyaltyCategory userCategory = null;
        for (LoyaltyCategory category : passedCategories) {
            if (category.getRequiredPoints() > currentMax) {
                userCategory = category;
                currentMax = category.getRequiredPoints();
            }
        }
        return userCategory;
    }
}
