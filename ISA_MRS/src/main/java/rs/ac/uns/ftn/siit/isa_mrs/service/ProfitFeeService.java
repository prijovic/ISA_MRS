package rs.ac.uns.ftn.siit.isa_mrs.service;

import org.springframework.http.ResponseEntity;
import rs.ac.uns.ftn.siit.isa_mrs.controller.ProfitController;
import rs.ac.uns.ftn.siit.isa_mrs.dto.ProfitFeeDto;

import java.util.Collection;
import java.util.List;

public interface ProfitFeeService {
    ResponseEntity<Collection<ProfitFeeDto>> getAllProfitFees();
    ResponseEntity<Collection<ProfitFeeDto>> setAllProfitFees(List<ProfitController.FeeRequest> fees);
}
