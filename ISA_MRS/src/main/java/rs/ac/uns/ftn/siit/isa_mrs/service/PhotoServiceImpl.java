package rs.ac.uns.ftn.siit.isa_mrs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.siit.isa_mrs.model.Photo;
import rs.ac.uns.ftn.siit.isa_mrs.repository.PhotoRepo;

import java.nio.file.Paths;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepo photoRepo;

    @Override
    public ResponseEntity<FileSystemResource> getPhoto(String id) {
        try {
            Optional<Photo> photo = photoRepo.findByPhoto(id);
            return photo.map(value -> new ResponseEntity<>(new FileSystemResource(
                    Paths.get("./src/main/java/rs/ac/uns/ftn/siit/isa_mrs/resources/photos/" + id + ".jpg")),
                            HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
