package rs.ac.uns.ftn.siit.isa_mrs.service;

import rs.ac.uns.ftn.siit.isa_mrs.model.Boat;

import java.util.Collection;
import java.util.Optional;

public interface BoatService {
    Optional<Boat> getBoat(Long id);
    Collection<Boat> getBoats();
}
