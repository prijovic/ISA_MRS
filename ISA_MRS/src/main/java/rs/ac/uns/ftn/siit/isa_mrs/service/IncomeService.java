package rs.ac.uns.ftn.siit.isa_mrs.service;

import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.AdminDtos.GraphDto;

public interface IncomeService {
    double getIncomeLastYear();
    double getIncomeLastMonth();
    double getIncomeLastWeek();
    GraphDto getYearlyReservationIncomeData();
    GraphDto getYearlyCancellationIncomeData();
}
