package rs.ac.uns.ftn.siit.isa_mrs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.SpecialOfferDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.SpecialOfferRentalDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.RentalObjectDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.*;
import rs.ac.uns.ftn.siit.isa_mrs.repository.*;

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
    private final VacationRentalRepo vacationRentalRepo;
    private final BoatRepo boatRepo;
    private final EmailSenderService emailSenderService;

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
            Collection<Client> subscribers = rentalObject.get().getSubscribers();
            for (Client client: subscribers) {
                emailSenderService.sendNewOfferEmail(client.getEmail(), specialOffer.getRentalObject().getName());
            }
            return new ResponseEntity<>(modelMapper.map(rentalObject, RentalObjectDto.class), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<RentalObjectDto> addSpecialOfferVacationRental(SpecialOfferRentalDto dto) {
        try {
            Collection<rs.ac.uns.ftn.siit.isa_mrs.model.Service> services = new ArrayList<>();
            dto.getIncludedServices().forEach(serviceDto -> {
                rs.ac.uns.ftn.siit.isa_mrs.model.Service service = modelMapper.map(serviceDto, rs.ac.uns.ftn.siit.isa_mrs.model.Service.class);
                serviceRepo.save(service);
                services.add(service);
            });
            Optional<VacationRental> rentalObject = vacationRentalRepo.findById(dto.getRentalId());
            if (rentalObject.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            VacationRental vacationRental = rentalObject.get();
            SpecialOffer specialOffer = new SpecialOffer();
            specialOffer.setCapacity(dto.getCapacity());
            specialOffer.setInitDate(dto.getInitDate().plusHours(2));
            specialOffer.setTermDate(dto.getTermDate().plusHours(2));
            specialOffer.setDiscount(dto.getDiscount());
            specialOffer.setIncludedServices(services);
            specialOffer.setRentalObject(vacationRental);
            specialOfferRepo.save(specialOffer);
            vacationRental.getSpecialOffers().add(specialOffer);
            rentalObjectRepo.save(vacationRental);
            services.forEach(service -> {
                service.setSpecialOffer(specialOffer);
                serviceRepo.save(service);
            });
            Collection<Client> subscribers = rentalObject.get().getSubscribers();
            for (Client client: subscribers) {
                emailSenderService.sendNewOfferEmail(client.getEmail(), specialOffer.getRentalObject().getName());
            }
            return new ResponseEntity<>(modelMapper.map(rentalObject, RentalObjectDto.class), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<RentalObjectDto> addSpecialOfferBoat(SpecialOfferRentalDto dto) {
        try {
            Collection<rs.ac.uns.ftn.siit.isa_mrs.model.Service> services = new ArrayList<>();
            dto.getIncludedServices().forEach(serviceDto -> {
                rs.ac.uns.ftn.siit.isa_mrs.model.Service service = modelMapper.map(serviceDto, rs.ac.uns.ftn.siit.isa_mrs.model.Service.class);
                serviceRepo.save(service);
                services.add(service);
            });
            Optional<Boat> rentalObject = boatRepo.findById(dto.getRentalId());
            if (rentalObject.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Boat boat = rentalObject.get();
            SpecialOffer specialOffer = new SpecialOffer();
            specialOffer.setCapacity(dto.getCapacity());
            specialOffer.setInitDate(dto.getInitDate().plusHours(2));
            specialOffer.setTermDate(dto.getTermDate().plusHours(2));
            specialOffer.setDiscount(dto.getDiscount());
            specialOffer.setIncludedServices(services);
            specialOffer.setRentalObject(boat);
            specialOfferRepo.save(specialOffer);
            boat.getSpecialOffers().add(specialOffer);
            rentalObjectRepo.save(boat);
            services.forEach(service -> {
                service.setSpecialOffer(specialOffer);
                serviceRepo.save(service);
            });
            Collection<Client> subscribers = rentalObject.get().getSubscribers();
            for (Client client: subscribers) {
                emailSenderService.sendNewOfferEmail(client.getEmail(), specialOffer.getRentalObject().getName());
            }
            return new ResponseEntity<>(modelMapper.map(rentalObject, RentalObjectDto.class), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
