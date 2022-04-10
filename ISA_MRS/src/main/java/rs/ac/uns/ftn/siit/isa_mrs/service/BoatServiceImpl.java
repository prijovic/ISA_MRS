package rs.ac.uns.ftn.siit.isa_mrs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.siit.isa_mrs.model.Boat;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;
import rs.ac.uns.ftn.siit.isa_mrs.repository.BoatRepo;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BoatServiceImpl implements BoatService{

    private final BoatRepo boatRepo;

    @Override
    public Optional<Boat> getBoat(Long id) {
        log.info("Getting boat by id {}.", id);
        return boatRepo.findById(id);
    }

    @Override
    public Collection<Boat> getBoats() {
        boatRepo.findByRentalObjectType(RentalObjectType.Boat).forEach(Boat -> log.info(Boat.toString()));
        return boatRepo.findByRentalObjectType(RentalObjectType.Boat);
    }
}
