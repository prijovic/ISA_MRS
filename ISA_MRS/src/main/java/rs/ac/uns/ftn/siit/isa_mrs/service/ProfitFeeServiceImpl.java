package rs.ac.uns.ftn.siit.isa_mrs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.isa_mrs.dto.ProfitFeeDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;
import rs.ac.uns.ftn.siit.isa_mrs.repository.ProfitFeeRepo;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfitFeeServiceImpl implements ProfitFeeService {
    private final ProfitFeeRepo profitFeeRepo;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<Collection<ProfitFeeDto>> getAllProfitFees() {
        try{
            Collection<ProfitFeeDto> profitFeeDtos = new ArrayList<>();
            for (RentalObjectType type : RentalObjectType.values()) {
                profitFeeRepo.findProfitFeeByRentalObjectType(type).ifPresent(profitFee -> {
                    profitFeeDtos.add(modelMapper.map(profitFee, ProfitFeeDto.class));
                });
            }
            return new ResponseEntity<>(profitFeeDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
