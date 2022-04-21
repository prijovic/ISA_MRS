package rs.ac.uns.ftn.siit.isa_mrs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.siit.isa_mrs.service.PhotoService;

import static rs.ac.uns.ftn.siit.isa_mrs.util.Paths.PHOTO_CONTROLLER;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(PHOTO_CONTROLLER)
public class PhotoController {
    private final PhotoService photoService;

    @GetMapping(value = "/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<FileSystemResource> getPhoto(@PathVariable String id) {
        return photoService.getPhoto(id);
    }


}
