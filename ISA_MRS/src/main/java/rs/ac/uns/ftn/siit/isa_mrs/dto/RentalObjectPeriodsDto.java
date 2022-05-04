package rs.ac.uns.ftn.siit.isa_mrs.dto;

import lombok.Data;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;

import java.io.Serializable;
import java.util.Collection;

@Data
public class RentalObjectPeriodsDto implements Serializable {
    private final Long id;
    private final String name;
    private final RentalObjectType rentalObjectType;
    private final Collection<TimePeriodDto> availabilityPeriods;
}
