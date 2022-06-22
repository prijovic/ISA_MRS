package rs.ac.uns.ftn.siit.isa_mrs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.SpecialOfferDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.RentalObjectDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.Adventure;
import rs.ac.uns.ftn.siit.isa_mrs.model.SpecialOffer;
import rs.ac.uns.ftn.siit.isa_mrs.repository.AdventureRepo;
import rs.ac.uns.ftn.siit.isa_mrs.repository.RentalObjectRepo;
import rs.ac.uns.ftn.siit.isa_mrs.repository.ServiceRepo;
import rs.ac.uns.ftn.siit.isa_mrs.repository.SpecialOfferRepo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class SpecialOfferServiceImpl implements SpecialOfferService {
    private final SpecialOfferRepo specialOfferRepo;
    private final RentalObjectRepo rentalObjectRepo;
    private final ServiceRepo serviceRepo;
    private final ModelMapper modelMapper;
    private final AdventureRepo adventureRepo;

    @Override
    public ResponseEntity<RentalObjectDto> addSpecialOffer(SpecialOfferDto dto) {
        try {
            Collection<rs.ac.uns.ftn.siit.isa_mrs.model.Service> services = new ArrayList<>();
            dto.getIncludedServices().forEach(serviceDto -> {
                rs.ac.uns.ftn.siit.isa_mrs.model.Service service = modelMapper.map(serviceDto, rs.ac.uns.ftn.siit.isa_mrs.model.Service.class);
                serviceRepo.save(service);
                services.add(service);
            });
            Optional<Adventure> rentalObject = adventureRepo.findById(dto.getRentalId());
            if (rentalObject.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Adventure adventure = rentalObject.get();
            SpecialOffer specialOffer = new SpecialOffer();
            specialOffer.setCapacity(dto.getCapacity());
            specialOffer.setInitDate(dto.getInitDate().plusHours(2));
            specialOffer.setDiscount(dto.getDiscount());
            specialOffer.setIncludedServices(services);
            double duration = adventure.getDuration() * 60;
            LocalDateTime termDate = dto.getInitDate().plusHours(2).plusMinutes((long) duration);
            specialOffer.setTermDate(termDate);
            specialOffer.setRentalObject(adventure);
            specialOfferRepo.save(specialOffer);
            adventure.getSpecialOffers().add(specialOffer);
            rentalObjectRepo.save(adventure);
            services.forEach(service -> {
                service.setSpecialOffer(specialOffer);
                serviceRepo.save(service);
            });
            return new ResponseEntity<>(modelMapper.map(rentalObject, RentalObjectDto.class), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
