package rs.ac.uns.ftn.siit.isa_mrs.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.AdminDtos.DashboardDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.AdminDtos.IncomeDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.ProfitFeeDto;
import rs.ac.uns.ftn.siit.isa_mrs.service.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static rs.ac.uns.ftn.siit.isa_mrs.util.Paths.INCOME_FEES;
import static rs.ac.uns.ftn.siit.isa_mrs.util.Paths.PROFITS;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(PROFITS)
public class ProfitController {
    private final ProfitFeeService profitFeeService;
    private final AdventureService adventureService;
    private final BoatService boatService;
    private final VacationRentalService vacationRentalService;
    private final ClientService clientService;
    private final RentalObjectOwnerService rentalObjectOwnerService;
    private final ReservationService reservationService;
    private final IncomeService incomeService;

    @GetMapping(INCOME_FEES)
    public ResponseEntity<Collection<ProfitFeeDto>> getProfitFees() {
        return profitFeeService.getAllProfitFees();
    }

    @PostMapping(INCOME_FEES)
    public ResponseEntity<Collection<ProfitFeeDto>> setProfitFees(@RequestBody FeeRequestsWrapper fees) {
        return profitFeeService.setAllProfitFees(fees.getFees());
    }

    @GetMapping("/adminDashboard")
    public ResponseEntity<DashboardDto> getDashboard() {
        try {
            DashboardDto dashboardDto = new DashboardDto();
            dashboardDto.setTotalAdventures(adventureService.countAdventure());
            dashboardDto.setTotalBoats(boatService.countBoat());
            dashboardDto.setTotalVacationRentals(vacationRentalService.countVacationRentals());
            dashboardDto.setTotalClients(clientService.countClients());
            dashboardDto.setTotalOwners(rentalObjectOwnerService.countRentalObjectOwners());
            dashboardDto.setReservationsLastYear(reservationService.countReservationsLastYear());
            dashboardDto.setReservationsLastMonth(reservationService.countReservationsLastMonth());
            dashboardDto.setReservationsLastWeek(reservationService.countReservationsLastWeek());
            dashboardDto.setIncomeLastYear(incomeService.getIncomeLastYear());
            dashboardDto.setIncomeLastMonth(incomeService.getIncomeLastMonth());
            dashboardDto.setIncomeLastWeek(incomeService.getIncomeLastWeek());
            dashboardDto.setTotalReservations(reservationService.countReservations());
            dashboardDto.setReservationIncomeGraph(incomeService.getYearlyReservationIncomeData());
            dashboardDto.setCancellationIncomeGraph(incomeService.getYearlyCancellationIncomeData());
            return new ResponseEntity<>(dashboardDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/adminReport")
    public ResponseEntity<Collection<IncomeDto>> getAdminReportData(@RequestParam String start, @RequestParam String end) {
        return incomeService.getAdminReportData(start, end);
    }

    @Data
    static class FeeRequestsWrapper {
        private List<FeeRequest> fees;
    }

    @Data
    public static class FeeRequest {
        private String rentalObjectType;
        private Double value;
    }
}
