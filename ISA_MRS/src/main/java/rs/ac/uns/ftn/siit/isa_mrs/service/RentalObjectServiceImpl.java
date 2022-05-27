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
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.RentalObjectDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.RentalObjectPeriodsDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.RentalObject;
import rs.ac.uns.ftn.siit.isa_mrs.model.TimePeriod;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;
import rs.ac.uns.ftn.siit.isa_mrs.repository.RentalObjectRepo;
import rs.ac.uns.ftn.siit.isa_mrs.repository.TimePeriodRepo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentalObjectServiceImpl implements RentalObjectService {
    private final RentalObjectRepo rentalObjectRepo;
    private final TimePeriodRepo timePeriodRepo;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<RentalObjectPeriodsDto> setAvailabilityPeriods(Long id, List<LocalDate> dates) {
        try {
            Optional<RentalObject> rentalObjectOptional = rentalObjectRepo.findById(id);
            if (rentalObjectOptional.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            List<TimePeriod> periods = makePeriods(dates);
            RentalObject rentalObject = rentalObjectOptional.get();
            for (TimePeriod period:periods) {
                period.setRentalObject(rentalObject);
                timePeriodRepo.save(period);
            }
            rentalObject.setAvailabilityPeriods(periods);
            rentalObjectRepo.save(rentalObject);
            RentalObjectPeriodsDto rentalObjectPeriodsDto = modelMapper.map(rentalObject, RentalObjectPeriodsDto.class);
            return new ResponseEntity<>(rentalObjectPeriodsDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<PageDto<RentalObjectDto>> getRentalObjects(int page, int pageSize) {
        try{
            Pageable pageable = PageRequest.of(page, pageSize).withSort(Sort.by(Sort.Order.asc("name")));
            Page<RentalObject> rentalsPage = rentalObjectRepo.findAll(pageable);
            return new ResponseEntity<>(packRentals(rentalsPage), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<PageDto<RentalObjectDto>> getRentalObjects(int page, int pageSize, String filter) {
        try{
            Pageable pageable = PageRequest.of(page, pageSize).withSort(Sort.by(Sort.Order.asc("name")));
            Page<RentalObject> rentalsPage = rentalObjectRepo.findAllByRentalObjectType(RentalObjectType.valueOf(filter), pageable);
            return new ResponseEntity<>(packRentals(rentalsPage), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private PageDto<RentalObjectDto> packRentals(Page<RentalObject> rentalObjectPage) {
        PageDto<RentalObjectDto> result = new PageDto<>();
        Collection<RentalObjectDto> rentalsDtos = new ArrayList<>();
        rentalObjectPage.getContent().forEach(rentalObject -> rentalsDtos.add(modelMapper.map(rentalObject, RentalObjectDto.class)));
        result.setContent(rentalsDtos);
        result.setPages(rentalObjectPage.getTotalPages());
        result.setCurrentPage(rentalObjectPage.getNumber());
        result.setPageSize(rentalObjectPage.getSize());
        return result;
    }

    private List<TimePeriod> makePeriods(List<LocalDate> dates){
        List<TimePeriod> timePeriods = new ArrayList<>();
        dates.sort(Comparator.naturalOrder());
        for (int i = 0; i < dates.size() - 1; i++) {
            LocalDate start = dates.get(i);
            if (timePeriods.size()!=0 &&
                    (start.isBefore(timePeriods.get(timePeriods.size()-1).getTermDate()) ||
                    start.isEqual(timePeriods.get(timePeriods.size()-1).getTermDate()))){
                continue;
            }
            LocalDate end = dates.get(i);
            for (int j = i+1; j < dates.size(); j++) {
                if (ChronoUnit.DAYS.between(dates.get(i), dates.get(j)) == 1){
                    end = dates.get(j);
                }
                else {
                    break;
                }
            }
            TimePeriod timePeriod = new TimePeriod();
            timePeriod.setInitDate(start);
            timePeriod.setTermDate(end);
            timePeriods.add(timePeriod);
        }
        return timePeriods;
    }
}
