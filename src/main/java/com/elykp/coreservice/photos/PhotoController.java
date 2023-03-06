package com.elykp.coreservice.photos;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.elykp.coreservice.tags.Tag;
import com.elykp.coreservice.tags.TagRepository;
import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {

  private final PhotoRepository photoRepository;

  private final TagRepository tagRepository;

  public PhotoController(PhotoRepository photoRepository, TagRepository tagRepository) {
    this.photoRepository = photoRepository;
    this.tagRepository = tagRepository;
  }

  @GetMapping
  public ResponseEntity<List<Photo>> getListPhoto() {
    return ResponseEntity.ok(photoRepository.findAll());
  }

  @GetMapping("{id}")
  public ResponseEntity<Photo> getById(@PathVariable String id) {
    Photo photo = photoRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    return ResponseEntity.ok(photo);
  }

  @PostMapping
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<Photo> create(
      JwtAuthenticationToken authentication,
      @Valid @RequestBody CreatePhotoDto createPhotoDto) {
    Photo photo = new Photo();

    photo.setId(NanoIdUtils.randomNanoId(
        NanoIdUtils.DEFAULT_NUMBER_GENERATOR,
        NanoIdUtils.DEFAULT_ALPHABET, 8));
    photo.setNsfw(createPhotoDto.getNsfw() != null ? createPhotoDto.getNsfw() : false);
    photo.setCreatorId(authentication.getTokenAttributes().get("sub").toString());
    photo.setTitle(createPhotoDto.getTitle());
    photo.setBlurhash(createPhotoDto.getBlurhash());
    photo.setLikeCount(0);

    for (String tag : createPhotoDto.getTags()) {
      Optional<Tag> existingTag = tagRepository.findByNameIgnoreCase(tag);
      if (existingTag.isPresent()) {
        photo.getTags().add(existingTag.get());
      } else {
        Tag newTag = new Tag();
        newTag.setName(tag);
        photo.getTags().add(tagRepository.save(newTag));
      }
    }
    return new ResponseEntity<>(photoRepository.save(photo), HttpStatus.CREATED);
  }
}