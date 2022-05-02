package rs.ac.uns.ftn.siit.isa_mrs.service;

import org.springframework.http.ResponseEntity;
import rs.ac.uns.ftn.siit.isa_mrs.dto.ProfitFeeDto;

import java.util.Collection;

public interface ProfitFeeService {
    ResponseEntity<Collection<ProfitFeeDto>> getAllProfitFees();
}
