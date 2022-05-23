package rs.ac.uns.ftn.siit.isa_mrs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto.AddVacationRentalDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.RequestDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.VacationRentalDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.*;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;
import rs.ac.uns.ftn.siit.isa_mrs.repository.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class VacationRentalServiceImpl implements VacationRentalService{

    private final VacationRentalRepo vacationRentalRepo;
    private final ModelMapper modelMapper;
    private final RentalObjectOwnerRepo ownerRepo;
    private final AddressRepo addressRepo;
    private final CancellationFeeRepo cancellationFeeRepo;
    private final AdditionalServiceRepo additionalServiceRepo;
    private final ConductRuleRepo conductRuleRepo;
    private final RoomRepo roomRepo;

    @Override
    public ResponseEntity<VacationRentalDto> getVacationRental(Long id) {
        try{
            Optional<VacationRental> rental = vacationRentalRepo.findById(id);
            return rental.map(vacationRental -> new ResponseEntity<>(modelMapper.map(vacationRental, VacationRentalDto.class), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<PageDto<VacationRentalDto>> findVacationRentalsWithPaginationSortedByField(
            int offset, int pageSize, String field) {
        PageDto<VacationRentalDto> result = new PageDto<>();
        try{
            Pageable pageable = PageRequest.of(offset, pageSize).withSort(Sort.by(field));
            Page<VacationRental> vacationRentalsPage = vacationRentalRepo
                    .findAllByRentalObjectType(RentalObjectType.VacationRental, pageable);
            Collection<VacationRentalDto> vacationRentalDtos = new ArrayList<>();
            vacationRentalsPage.getContent().forEach(vacationRental -> {
                vacationRentalDtos.add(modelMapper.map(vacationRental, VacationRentalDto.class));
            });
            result.setContent(vacationRentalDtos);
            result.setPages(vacationRentalsPage.getTotalPages());
            result.setCurrentPage(vacationRentalsPage.getNumber() + 1);
            result.setPageSize(vacationRentalsPage.getSize());
            if (vacationRentalsPage.getContent().isEmpty()) {
                return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
            }
            else {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<VacationRentalDto> addNewVacationRental(AddVacationRentalDto vrd) {
        log.info("Uslo u servis");
        log.info(vrd.getName());
        log.info(vrd.getOwnerEmail());
        Optional<RentalObjectOwner> owner = ownerRepo.findByEmail(vrd.getOwnerEmail());
        log.info(String.valueOf(owner));
        if (owner.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        else{
            try{
                RentalObjectOwner vacationRentalOwner = owner.get();
                Address address = new Address();
                address.setCountry(vrd.getAddress().getCountry());
                address.setCity(vrd.getAddress().getCity());
                address.setStreet(vrd.getAddress().getStreet());
                address.setNumber(vrd.getAddress().getNumber());
                address.setLatitude(vrd.getAddress().getLatitude());
                address.setLongitude(vrd.getAddress().getLongitude());
                addressRepo.save(address);
                log.info("Adresa napravljena");

                CancellationFee cancellationFee = new CancellationFee();
                cancellationFee.setFeeType(vrd.getCancellationFee().getFeeType());
                cancellationFee.setValue(vrd.getCancellationFee().getValue());
                cancellationFeeRepo.save(cancellationFee);

                VacationRental vacationRental = new VacationRental();
                vacationRental.setName(vrd.getName());
                vacationRental.setRentalObjectType(RentalObjectType.VacationRental);
                vacationRental.setDescription(vrd.getDescription());
                vacationRental.setPrice(vrd.getPrice());
                vacationRental.setCapacity(vrd.getCapacity());
                vacationRental.setRentalObjectOwner(vacationRentalOwner);
                vacationRental.setAddress(address);
                vacationRental.setCancellationFee(cancellationFee);

                Collection<AdditionalService> additionalServices = new ArrayList<>();
                for(var item : vrd.getAdditionalServices()){
                    AdditionalService additionalService = new AdditionalService();
                    additionalService.setName(item.getName());
                    additionalService.setPrice(item.getPrice());
                    additionalServiceRepo.save(additionalService);
                    additionalServices.add(additionalService);
                }
                log.info("Dodatne usluge napravljene");
                vacationRental.setAdditionalServices(additionalServices);

                Collection<ConductRule> conductRules = new ArrayList<>();
                for(var item : vrd.getConductRules()){
                    ConductRule conductRule = new ConductRule();
                    conductRule.setType(item.getType());
                    conductRule.setRule(item.getRule());
                    conductRuleRepo.save(conductRule);
                    conductRules.add(conductRule);
                }
                vacationRental.setConductRules(conductRules);

                Collection<Room> rooms = new ArrayList<>();
                for(var item : vrd.getRooms()){
                    Room room = new Room();
                    room.setBeds(item.getBeds());
                    roomRepo.save(room);
                    rooms.add(room);
                }
                vacationRental.setRooms(rooms);
                log.info("Dodate sobe");
                Collection<Photo> photos = new ArrayList<>();
                for(var item : vrd.getPhotos()){
                    Photo photo = new Photo();
                    photo.setPhoto(item.getPhoto());
                    photos.add(photo);
                }
                vacationRental.setPhotos(photos);
                vacationRentalRepo.save(vacationRental);
                VacationRentalDto RentalDto = modelMapper.map(vacationRental, VacationRentalDto.class);

                return new ResponseEntity<>(RentalDto, HttpStatus.OK);
            } catch (IllegalArgumentException e) {
                log.error(e.getMessage());
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                log.error(e.getMessage());
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
}
