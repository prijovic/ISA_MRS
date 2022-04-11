package rs.ac.uns.ftn.siit.isa_mrs.service;

import rs.ac.uns.ftn.siit.isa_mrs.model.VacationRental;

import java.util.Collection;
import java.util.Optional;

public interface VacationRentalService {
    Optional<VacationRental> getVacationRental(Long id);
    Collection<VacationRental> getVacationRentals();
}
