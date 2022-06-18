package rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.AdminDtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class DashboardDto  implements Serializable {
    private long totalReservations;
    private long reservationsLastYear;
    private long reservationsLastMonth;
    private long reservationsLastWeek;
    private long totalOwners;
    private long totalClients;
    private long totalVacationRentals;
    private long totalBoats;
    private long totalAdventures;
    private double incomeLastYear;
    private double incomeLastMonth;
    private double incomeLastWeek;
    private GraphDto reservationIncomeGraph;
    private GraphDto cancellationIncomeGraph;
}
