package rs.ac.uns.ftn.siit.isa_mrs.util;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.siit.isa_mrs.model.TimePeriod;
import rs.ac.uns.ftn.siit.isa_mrs.repository.TimePeriodRepo;

import java.time.LocalDate;
import java.util.Collection;


@Component
@RequiredArgsConstructor
public class Scheduler {
    private final TimePeriodRepo timePeriodRepo;
    
    @Scheduled(cron = "0 0 3 * * 1,3,6")
    void chargeDoneReservations() {
        LocalDate today = LocalDate.now();
        LocalDate twoDaysAgo = today.minusDays(2);
        Collection<TimePeriod> timePeriods =
                timePeriodRepo.findAllByInitDateBetweenAndAndReservationIsNotNull(twoDaysAgo, today);
    }
}
