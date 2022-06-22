package rs.ac.uns.ftn.siit.isa_mrs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.BoatOwnerDtos.BoatOwnerProfileDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.BoatOwnerDtos.BoatOwnerReservationDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.InstructorDtos.InstructorDtos.SubscriberDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.ReviewDtos.ReviewDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.ReservationDtos.ReservationIncomeDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.ReservationDtos.ReservationRentalObjectDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.ReservationDtos.ReservationReportDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.VacationRentalOwnerDtos.VacationRentalOwnerProfileDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.VacationRentalOwnerDtos.VacationRentalOwnerReservationDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PhotoDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.*;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RequestStatus;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.ReviewType;
import rs.ac.uns.ftn.siit.isa_mrs.repository.IncomeRepo;
import rs.ac.uns.ftn.siit.isa_mrs.repository.RentalObjectOwnerRepo;
import rs.ac.uns.ftn.siit.isa_mrs.repository.RentalObjectRepo;
import rs.ac.uns.ftn.siit.isa_mrs.security.JwtDecoder;
import java.util.*;
import rs.ac.uns.ftn.siit.isa_mrs.dto.AdditionalServiceDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.ClientDtos.ClientReservationLimitsDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.InstructorDtos.TimePeriodDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.RentalObject;
import rs.ac.uns.ftn.siit.isa_mrs.model.RentalObjectOwner;
import rs.ac.uns.ftn.siit.isa_mrs.repository.ReservationRepo;


@Slf4j
@Service
@RequiredArgsConstructor
public class RentalObjectOwnerServiceImpl implements RentalObjectOwnerService{

    private final RentalObjectOwnerRepo rentalObjectOwnerRepo;
    private final RentalObjectOwnerRepo ownerRepo;
    private final RentalObjectRepo rentalRepo;
    private final JwtDecoder jwtDecoder;
    private final ModelMapper modelMapper;
    private final IncomeRepo incomeRepo;
    private final RentalObjectServiceImpl rosi;

    @Override
    public long countRentalObjectOwners() {
        return rentalObjectOwnerRepo.findAllByIsActive(true).size();
    }

    @Override
    public ResponseEntity<BoatOwnerProfileDto> getBoatOwner(String token) {
        try{
            JwtDecoder.DecodedToken decodedToken;
            try {
                decodedToken = jwtDecoder.decodeToken(token);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            Optional<RentalObjectOwner> owner = ownerRepo.findByEmail(decodedToken.getEmail());
            if(owner.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            BoatOwnerProfileDto ownerDto = modelMapper.map(owner, BoatOwnerProfileDto.class);
            RentalObjectOwner boatOwner = owner.get();
            ownerDto.setGrade(rosi.calculateOwnerRating(boatOwner));
            ownerDto.setClientReviews(getOwnerReviews(boatOwner));
            ownerDto.setSubscribers(setUpSubscribers(boatOwner.getRentalObjects()));
            return new ResponseEntity<>(ownerDto, HttpStatus.OK);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ClientReservationLimitsDto> getReservationLimits(long rentalId, long ownerId) {
        try{
            Optional<RentalObjectOwner> roo = ownerRepo.findById(ownerId);
            if(roo.isEmpty()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            RentalObjectOwner owner = roo.get();
            ClientReservationLimitsDto result = new ClientReservationLimitsDto();
            result.setOwnerInitDate(owner.getInitDate());
            result.setOwnerTermDate(owner.getTermDate());
            Optional<RentalObject> rentalObject = rentalRepo.findById(rentalId);
            if(rentalObject.isEmpty()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            result.setRentalInitDate(rentalObject.get().getInitDate());
            result.setRentalTermDate(rentalObject.get().getTermDate());
            Collection<TimePeriodDto> timePeriodDtos = new ArrayList<>();
            rentalObject.get().getReservations().forEach(reservation -> {
                timePeriodDtos.add(new TimePeriodDto(reservation.getInitDate(), reservation.getTermDate()));
            });
            result.setReservationsPeriods(timePeriodDtos);

            Collection<AdditionalServiceDto> addServDto = new ArrayList<>();
            rentalObject.get().getAdditionalServices().forEach(service ->
                addServDto.add(modelMapper.map(service, AdditionalServiceDto.class)));

            result.setAdditionalServices(addServDto);
            result.setPrice(rentalObject.get().getPrice());

            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<VacationRentalOwnerProfileDto> getVacationRentalOwner(String token) {
        try{
            JwtDecoder.DecodedToken decodedToken;
            try {
                decodedToken = jwtDecoder.decodeToken(token);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            Optional<RentalObjectOwner> owner = ownerRepo.findByEmail(decodedToken.getEmail());
            if(owner.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            VacationRentalOwnerProfileDto ownerDto = modelMapper.map(owner, VacationRentalOwnerProfileDto.class);
            RentalObjectOwner boatOwner = owner.get();
            ownerDto.setGrade(rosi.calculateOwnerRating(boatOwner));
            ownerDto.setClientReviews(getOwnerReviews(boatOwner));
            ownerDto.setSubscribers(setUpSubscribers(boatOwner.getRentalObjects()));
            return new ResponseEntity<>(ownerDto, HttpStatus.OK);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Collection<BoatOwnerReservationDto>> getAllBoatOwnerReservations(String token) {
        try{
            JwtDecoder.DecodedToken decodedToken;
            try {
                decodedToken = jwtDecoder.decodeToken(token);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            Optional<RentalObjectOwner> owner = ownerRepo.findByEmail(decodedToken.getEmail());
            if(owner.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            RentalObjectOwner boatOwner = owner.get();
            Collection<BoatOwnerReservationDto> reservationDtos = new ArrayList<>();
            reservationDtos = setUpReservationDtos(boatOwner.getRentalObjects());
            return new ResponseEntity<>(reservationDtos, HttpStatus.OK);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Collection<VacationRentalOwnerReservationDto>> getAllVacationRentalOwnerReservations(String token) {
        try{
            JwtDecoder.DecodedToken decodedToken;
            try {
                decodedToken = jwtDecoder.decodeToken(token);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            Optional<RentalObjectOwner> owner = ownerRepo.findByEmail(decodedToken.getEmail());
            if(owner.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            RentalObjectOwner vacationRentalOwner = owner.get();
            Collection<VacationRentalOwnerReservationDto> reservationDtos = new ArrayList<>();
            reservationDtos = setUpVacationRentalReservationDtos(vacationRentalOwner.getRentalObjects());
            return new ResponseEntity<>(reservationDtos, HttpStatus.OK);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Collection<BoatOwnerReservationDto> setUpReservationDtos(Collection<RentalObject> boats) {
        Collection<BoatOwnerReservationDto> reservationDtos = new ArrayList<>();
        for(var a : boats) {
            for(var reservation : a.getReservations()) {
                BoatOwnerReservationDto reservationDto = modelMapper.map(reservation, BoatOwnerReservationDto.class);
                reservationDto.setReports(setUpReservationReports(reservation));
                reservationDto.setRentalObject(setUpReservationRentalObjectDto(reservation.getRentalObject()));
                reservationDto.setIncome(setUpIncomeDto(reservation.getId()));
                reservationDtos.add(reservationDto);
            }
        }
        return reservationDtos;
    }

    private Collection<VacationRentalOwnerReservationDto> setUpVacationRentalReservationDtos(Collection<RentalObject> vacationRentals) {
        Collection<VacationRentalOwnerReservationDto> reservationDtos = new ArrayList<>();
        for(var a : vacationRentals) {
            for(var reservation : a.getReservations()) {
                VacationRentalOwnerReservationDto reservationDto = modelMapper.map(reservation, VacationRentalOwnerReservationDto.class);
                reservationDto.setReports(setUpReservationReports(reservation));
                reservationDto.setRentalObject(setUpReservationRentalObjectDto(reservation.getRentalObject()));
                reservationDto.setIncome(setUpIncomeDto(reservation.getId()));
                reservationDtos.add(reservationDto);
            }
        }
        return reservationDtos;
    }

    private Collection<ReservationReportDto> setUpReservationReports(Reservation reservation) {
        Collection<ReservationReportDto> reports = new ArrayList<>();
        for(var report : reservation.getReports()) {
            ReservationReportDto r = modelMapper.map(report, ReservationReportDto.class);
            r.setUser(report.getAuthor().getUserType());
            reports.add(r);
        }
        return reports;
    }

    private ReservationRentalObjectDto setUpReservationRentalObjectDto(RentalObject rental) {
        ReservationRentalObjectDto rentalDto = modelMapper.map(rental, ReservationRentalObjectDto.class);
        if(rental.getPhotos().size() != 0) {
            Optional<Photo> photo = rental.getPhotos().stream().findFirst();
            if(photo.isPresent()) {
                PhotoDto photoDto = modelMapper.map(photo, PhotoDto.class);
                rentalDto.setDisplayPhoto(photoDto);
            }
        }
        return rentalDto;
    }

    private ReservationIncomeDto setUpIncomeDto(Long id) {
        Optional<Income> income = incomeRepo.findByReservationId(id);
        if(income.isEmpty()) return null;
        return modelMapper.map(income.get(), ReservationIncomeDto.class);
    }

    private Collection<ReviewDto> getOwnerReviews(RentalObjectOwner owner) {
        Collection<RentalObject> rentals = rentalRepo.findAllByRentalObjectOwner(owner);
        Collection<ReviewDto> reviews = new ArrayList<>();
        for(var rental : rentals) {
            for(var reservation : rental.getReservations()) {
                for(var review : reservation.getReviews())
                    if(review.getReviewType() == ReviewType.OwnerReview && review.getAuthor().getIsActive() && review.getStatus() == RequestStatus.Accepted)
                        reviews.add(modelMapper.map(review, ReviewDto.class));
            }
        }
        return reviews;
    }

    private Collection<SubscriberDto> setUpSubscribers(Collection<RentalObject> objects) {
        Collection<SubscriberDto> subscribers = new ArrayList<>();
        Set<Client> clients = new LinkedHashSet<>();
        for(var a : objects) {
            clients.addAll(a.getSubscribers());
        }
        clients.forEach(client -> subscribers.add(modelMapper.map(client, SubscriberDto.class)));
        return subscribers;
    }
}
