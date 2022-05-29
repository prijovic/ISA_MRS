package rs.ac.uns.ftn.siit.isa_mrs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.siit.isa_mrs.service.PhotoService;

import static rs.ac.uns.ftn.siit.isa_mrs.util.Paths.PHOTO_CONTROLLER;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(PHOTO_CONTROLLER)
public class PhotoController {
    private final PhotoService photoService;

    @GetMapping(value = "/", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<InputStreamResource> getPhoto(@RequestParam String path) {
        return photoService.getPhoto(path);
    }
}
