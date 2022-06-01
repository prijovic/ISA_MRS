package rs.ac.uns.ftn.siit.isa_mrs.service;

import org.springframework.http.ResponseEntity;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.ClientDtos.ClientProfileDto;

public interface ClientService {
    ResponseEntity<ClientProfileDto> getClient(String token);

}
