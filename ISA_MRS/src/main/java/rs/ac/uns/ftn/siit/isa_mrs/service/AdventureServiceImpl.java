package rs.ac.uns.ftn.siit.isa_mrs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.siit.isa_mrs.model.Adventure;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;
import rs.ac.uns.ftn.siit.isa_mrs.repository.AdventureRepo;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AdventureServiceImpl implements AdventureService{

    private final AdventureRepo adventureRepo;

    @Override
    public Optional<Adventure> getAdventure(Long id) {
        log.info("Getting adventure by id {}.", id);
        return adventureRepo.findById(id);
    }

    @Override
    public Collection<Adventure> getAdventures() {
        adventureRepo.findByRentalObjectType(RentalObjectType.Adventure).forEach(Adventure -> log.info(Adventure.toString()));
        return adventureRepo.findByRentalObjectType(RentalObjectType.Adventure);
    }
}