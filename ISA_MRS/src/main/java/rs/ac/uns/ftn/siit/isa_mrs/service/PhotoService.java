package rs.ac.uns.ftn.siit.isa_mrs.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface PhotoService {
    ResponseEntity<InputStreamResource> getPhoto(String photo);
    ResponseEntity<String> savePhoto(MultipartFile photo);
}
