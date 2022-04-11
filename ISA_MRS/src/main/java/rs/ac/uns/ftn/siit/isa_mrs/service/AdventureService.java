package rs.ac.uns.ftn.siit.isa_mrs.service;

import rs.ac.uns.ftn.siit.isa_mrs.model.Adventure;

import java.util.Collection;
import java.util.Optional;

public interface AdventureService {
    Optional<Adventure> getAdventure(Long id);
    Collection<Adventure> getAdventures();
}