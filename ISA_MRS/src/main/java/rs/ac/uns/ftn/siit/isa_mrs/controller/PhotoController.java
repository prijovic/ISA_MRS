package rs.ac.uns.ftn.siit.isa_mrs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rs.ac.uns.ftn.siit.isa_mrs.service.PhotoService;

import java.io.IOException;

import static rs.ac.uns.ftn.siit.isa_mrs.util.Paths.CROSS_ORIGIN;
import static rs.ac.uns.ftn.siit.isa_mrs.util.Paths.PHOTO_CONTROLLER;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(CROSS_ORIGIN)
@RequestMapping(PHOTO_CONTROLLER)
public class PhotoController {
    private final PhotoService photoService;

    @GetMapping(value = "/", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<InputStreamResource> getPhoto(@RequestParam String path) {
        return photoService.getPhoto(path);
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return photoService.savePhoto(file);
    }
}
