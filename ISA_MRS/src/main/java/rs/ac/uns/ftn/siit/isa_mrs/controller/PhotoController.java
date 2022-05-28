package rs.ac.uns.ftn.siit.isa_mrs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.siit.isa_mrs.service.PhotoService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Collection;


import static rs.ac.uns.ftn.siit.isa_mrs.util.Paths.PHOTO_CONTROLLER;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(PHOTO_CONTROLLER)
public class PhotoController {
    private final PhotoService photoService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable String id) {
        return photoService.getPhoto(id);
    }

    @GetMapping(value = "/get-image-with-media-type", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody Collection<byte[]> getPhotos(Collection<String> photos) throws IOException {
        InputStream in = getClass()
                .getResourceAsStream("/src/resources/static/photos/image.jpg" );

        //return IOUtils.toByteArray(in);
        return null;
    }

}
