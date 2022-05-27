package rs.ac.uns.ftn.siit.isa_mrs.service;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

public interface PhotoService {
    ResponseEntity<InputStreamResource> getPhoto(String photo);
}
