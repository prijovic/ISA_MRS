package rs.ac.uns.ftn.siit.isa_mrs.service;

import org.springframework.http.ResponseEntity;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.AdminDtos.GraphDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.AdminDtos.IncomeDto;

import java.time.LocalDateTime;
import java.util.Collection;

public interface IncomeService {
    double getIncomeLastYear();
    double getIncomeLastMonth();
    double getIncomeLastWeek();
    GraphDto getYearlyReservationIncomeData();
    GraphDto getYearlyCancellationIncomeData();
    ResponseEntity<Collection<IncomeDto>> getAdminReportData(LocalDateTime start, LocalDateTime end);
}
