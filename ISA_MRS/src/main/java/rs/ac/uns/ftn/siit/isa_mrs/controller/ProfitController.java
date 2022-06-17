package rs.ac.uns.ftn.siit.isa_mrs.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.siit.isa_mrs.dto.ProfitFeeDto;
import rs.ac.uns.ftn.siit.isa_mrs.service.ProfitFeeService;

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

    @GetMapping(INCOME_FEES)
    public ResponseEntity<Collection<ProfitFeeDto>> getProfitFees() {
        return profitFeeService.getAllProfitFees();
    }

    @PostMapping(INCOME_FEES)
    public ResponseEntity<Collection<ProfitFeeDto>> setProfitFees(@RequestBody FeeRequestsWrapper fees) {
        return profitFeeService.setAllProfitFees(fees.getFees());
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
