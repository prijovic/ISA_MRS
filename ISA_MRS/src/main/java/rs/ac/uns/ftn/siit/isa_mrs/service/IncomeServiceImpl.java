package rs.ac.uns.ftn.siit.isa_mrs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.AdminDtos.GraphDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.AdminDtos.GraphNodeDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.AdminDtos.IncomeDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.Income;
import rs.ac.uns.ftn.siit.isa_mrs.model.RentalObjectOwner;
import rs.ac.uns.ftn.siit.isa_mrs.repository.IncomeRepo;
import rs.ac.uns.ftn.siit.isa_mrs.repository.RentalObjectOwnerRepo;
import rs.ac.uns.ftn.siit.isa_mrs.security.JwtDecoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
@RequiredArgsConstructor
public class IncomeServiceImpl implements IncomeService{
    private final IncomeRepo incomeRepo;
    private final ModelMapper modelMapper;
    private final JwtDecoder jwtDecoder;
    private final RentalObjectOwnerRepo rentalObjectOwnerRepo;
    private final LoyaltyProgramService loyaltyProgramService;

    @Override
    public double getIncomeLastYear() {
        return sumIncome(incomeRepo.findAllByTimeStampBetween(LocalDateTime.now().minusYears(1), LocalDateTime.now()));
    }

    @Override
    public double getIncomeLastMonth() {
        return sumIncome(incomeRepo.findAllByTimeStampBetween(LocalDateTime.now().minusMonths(1), LocalDateTime.now()));
    }

    @Override
    public double getIncomeLastWeek() {
        return sumIncome(incomeRepo.findAllByTimeStampBetween(LocalDateTime.now().minusWeeks(1), LocalDateTime.now()));
    }

    @Override
    public GraphDto getYearlyReservationIncomeData() {
        GraphDto graph = new GraphDto();
        LocalDate date = LocalDate.now();
        int currentMonth = date.getMonthValue();
        for (int i = 0; i < 12; i++) {
            int calculatedMonth = ((currentMonth<1)?12+currentMonth:currentMonth);
            date = date.withMonth(calculatedMonth);
            LocalDate firstDay = date.withDayOfMonth(1);
            LocalDate lastDay = date.with(TemporalAdjusters.lastDayOfMonth());
            GraphNodeDto node = new GraphNodeDto();
            LocalDateTime start = LocalDateTime.of(firstDay, LocalTime.parse("00:00:00"));
            LocalDateTime end = LocalDateTime.of(lastDay, LocalTime.parse("00:00:00"));
            node.setValue(sumReservationIncome(incomeRepo.findAllByTimeStampBetween(start, end)));
            node.setMonth(date.getMonth().getDisplayName(TextStyle.FULL, Locale.US));
            graph.getNodes().add(node);
            currentMonth--;
        }
        return graph;
    }

    @Override
    public GraphDto getYearlyCancellationIncomeData() {
        GraphDto graph = new GraphDto();
        LocalDate date = LocalDate.now();
        int currentMonth = date.getMonthValue();
        for (int i = 0; i < 12; i++) {
            int calculatedMonth = ((currentMonth<1)?12+currentMonth:currentMonth);
            date = date.withMonth(calculatedMonth);
            LocalDate firstDay = date.withDayOfMonth(1);
            LocalDate lastDay = date.with(TemporalAdjusters.lastDayOfMonth());
            GraphNodeDto node = new GraphNodeDto();
            LocalDateTime start = LocalDateTime.of(firstDay, LocalTime.parse("00:00:00"));
            LocalDateTime end = LocalDateTime.of(lastDay, LocalTime.parse("00:00:00"));
            node.setValue(sumCancellationIncome(incomeRepo.findAllByTimeStampBetween(start, end)));
            node.setMonth(date.getMonth().getDisplayName(TextStyle.FULL, Locale.US));
            graph.getNodes().add(node);
            currentMonth--;
        }
        return graph;
    }

    @Override
    public ResponseEntity<Collection<IncomeDto>> getAdminReportData(String start, String end) {
        try {
            Collection<IncomeDto> reportData = new ArrayList<>();
            LocalDateTime startDate = LocalDateTime.parse(start);
            LocalDateTime endDate = LocalDateTime.parse(end);
            Collection<Income> incomes = incomeRepo.findAllByTimeStampBetweenOrderByTimeStamp(startDate, endDate);
            incomes.forEach(income -> {
                IncomeDto dto = modelMapper.map(income, IncomeDto.class);
                dto.setType(income.getReservation().getCancelled()?"cancellation":"reservation");
                reportData.add(dto);
            });
            return new ResponseEntity<>(reportData, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Collection<IncomeDto>> getInstructorReportData(String start, String end, String token) {
        try {
            Collection<IncomeDto> reportData = new ArrayList<>();
            JwtDecoder.DecodedToken decodedToken;
            GraphDto graph = new GraphDto();
            try {
                decodedToken = jwtDecoder.decodeToken(token);
            } catch (Exception e) {
                return null;
            }
            Optional<RentalObjectOwner> user = rentalObjectOwnerRepo.findByEmail(decodedToken.getEmail());
            if (user.isPresent()) {
                RentalObjectOwner owner = user.get();
                LocalDateTime startDate = LocalDateTime.parse(start);
                LocalDateTime endDate = LocalDateTime.parse(end);
                Collection<Income> incomes = incomeRepo.findAllByTimeStampBetweenAndReservationRentalObjectRentalObjectOwnerOrderByTimeStamp(startDate, endDate, owner);
                incomes.forEach(income -> {
                    IncomeDto dto = modelMapper.map(income, IncomeDto.class);
                    dto.setValue(calculateInstructorIncome(income));
                    dto.setType(income.getReservation().getCancelled()?"cancellation":"reservation");
                    reportData.add(dto);
                });
            }
            return new ResponseEntity<>(reportData, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private double calculateInstructorIncome(Income income) {
        AtomicReference<Double> instructorIncome = new AtomicReference<>(income.getReservation().getRentalObject().getPrice());
        income.getReservation().getAdditionalServices().forEach(additionalService ->  {
            instructorIncome.updateAndGet(v -> (double) (v + additionalService.getPrice()));
        });
        return (instructorIncome.get() - income.getValue()) + (instructorIncome.get() - income.getValue()) * loyaltyProgramService.getOwnersBenefit(income.getReservation().getRentalObject().getRentalObjectOwner())/100;
    }

    private double sumIncome(Collection<Income> incomes) {
        AtomicReference<Double> totalIncome = new AtomicReference<>((double) 0);
        incomes.forEach(income -> {
            totalIncome.updateAndGet(v -> (double) (v + income.getValue()));
        });
        return totalIncome.get();
    }

    private double sumReservationIncome(Collection<Income> incomes) {
        AtomicReference<Double> totalIncome = new AtomicReference<>((double) 0);
        incomes.forEach(income -> {
            if (!income.getReservation().getCancelled()) {
                totalIncome.updateAndGet(v -> (double) (v + income.getValue()));

            }
        });
        return totalIncome.get();
    }

    private double sumCancellationIncome(Collection<Income> incomes) {
        AtomicReference<Double> totalIncome = new AtomicReference<>((double) 0);
        incomes.forEach(income -> {
            if (income.getReservation().getCancelled()) {
                totalIncome.updateAndGet(v -> (double) (v + income.getValue()));

            }
        });
        return totalIncome.get();
    }
}
