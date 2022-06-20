package rs.ac.uns.ftn.siit.isa_mrs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.isa_mrs.repository.RentalObjectOwnerRepo;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentalObjectOwnerServiceImpl implements RentalObjectOwnerService{
    private final RentalObjectOwnerRepo rentalObjectOwnerRepo;

    @Override
    public long countRentalObjectOwners() {
        return rentalObjectOwnerRepo.findAllByIsActive(true).size();
    }
}
