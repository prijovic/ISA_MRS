package rs.ac.uns.ftn.siit.isa_mrs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.AdminDtos.GraphDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.AdminDtos.GraphNodeDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.Income;
import rs.ac.uns.ftn.siit.isa_mrs.repository.IncomeRepo;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.Instant;
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
