package rs.ac.uns.ftn.siit.isa_mrs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {

    @Override
    public ResponseEntity<InputStreamResource> getPhoto(String path) {
        try {
            var imgFile = new FileSystemResource("src/main/resources/static/photos/" + path + ".jpg");
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(new InputStreamResource(imgFile.getInputStream()));
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> savePhoto(MultipartFile photo) {
        try {
            String name = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) + randomStringGenerator();
            name = name.replaceAll("[: .]", "");
            Files.copy(photo.getInputStream(), Paths.get("src\\main\\resources\\static").resolve("photos").resolve(name + ".jpg").toAbsolutePath());
            return new ResponseEntity<>(name, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String randomStringGenerator() {
        int length = 20;
        boolean useLetters = true;
        boolean useNumbers = true;
        return RandomStringUtils.random(length, useLetters, useNumbers);
    }
}
