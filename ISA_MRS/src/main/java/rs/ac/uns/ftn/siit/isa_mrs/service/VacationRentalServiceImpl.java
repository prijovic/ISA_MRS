package rs.ac.uns.ftn.siit.isa_mrs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.siit.isa_mrs.model.VacationRental;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;
import rs.ac.uns.ftn.siit.isa_mrs.repository.VacationRentalRepo;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class VacationRentalServiceImpl implements VacationRentalService{

    private final VacationRentalRepo vacationRentalRepo;

    @Override
    public Optional<VacationRental> getVacationRental(Long id) {
        log.info("Getting vacation rental by id {}.", id);
        return vacationRentalRepo.findById(id);
    }

    @Override
    public Collection<VacationRental> getVacationRentals() {
        vacationRentalRepo.findByRentalObjectType(RentalObjectType.VacationRental).forEach(vacationRental -> log.info(vacationRental.toString()));
        return vacationRentalRepo.findByRentalObjectType(RentalObjectType.VacationRental);
    }
}
