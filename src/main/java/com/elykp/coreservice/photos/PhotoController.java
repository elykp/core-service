package com.elykp.coreservice.photos;

import com.elykp.coreservice.photos.domain.CreatePhotoRQ;
import com.elykp.coreservice.photos.domain.PhotoRS;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/photos")
public class PhotoController {

  private final PhotoRepository photoRepository;

  private final PhotoService photoService;


  @GetMapping
  public ResponseEntity<List<Photo>> getListPhoto() {
    return ResponseEntity.ok(photoRepository.findAll());
  }

  @GetMapping("{id}")
  public ResponseEntity<PhotoRS> getById(@PathVariable String id) {
    return ResponseEntity.ok(photoService.getById(id));
  }

  @PostMapping
  public ResponseEntity<PhotoRS> create(
      JwtAuthenticationToken authentication,
      @Valid @RequestBody CreatePhotoRQ createPhotoRQ) {
    PhotoRS photo = photoService.createPhoto(createPhotoRQ,
        authentication.getTokenAttributes().get("sub").toString());
    return new ResponseEntity<>(photo, HttpStatus.CREATED);
  }
}