package rs.ac.uns.ftn.siit.isa_mrs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.isa_mrs.dto.AdditionalServiceDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.ClientDtos.ClientReservationLimitsDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.InstructorDtos.TimePeriodDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.ReservationDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.RentalObject;
import rs.ac.uns.ftn.siit.isa_mrs.model.RentalObjectOwner;
import rs.ac.uns.ftn.siit.isa_mrs.repository.RentalObjectOwnerRepo;
import rs.ac.uns.ftn.siit.isa_mrs.repository.RentalObjectRepo;
import rs.ac.uns.ftn.siit.isa_mrs.repository.ReservationRepo;
import rs.ac.uns.ftn.siit.isa_mrs.security.JwtDecoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentalObjectOwnerServiceImpl implements RentalObjectOwnerService{

    private final RentalObjectOwnerRepo rentalObjectOwnerRepo;
    private final RentalObjectOwnerRepo ownerRepo;
    private final RentalObjectRepo rentalRepo;
    private final ReservationRepo reservationRepo;
    private final JwtDecoder jwtDecoder;
    private final ModelMapper modelMapper;

    @Override
    public long countRentalObjectOwners() {
        return rentalObjectOwnerRepo.findAllByIsActive(true).size();
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

}
