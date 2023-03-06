package com.elykp.coreservice.tags;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.jaxb.SpringDataJaxb.PageRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping(value = "/api/tags")
public class TagController {
  private final TagRepository tagRepository;

  public TagController(TagRepository tagRepository) {
    this.tagRepository = tagRepository;
  }

  @PostMapping
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<Tag> create(@RequestBody CreateTagDto createTagDto) throws DataIntegrityViolationException {
    try {
      Tag tag = new Tag();
      tag.setName(createTagDto.getName());
      return new ResponseEntity<>(tagRepository.save(tag), HttpStatus.CREATED);
    } catch (DataIntegrityViolationException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tag already exists");
    }
  }

  @GetMapping
  public ResponseEntity<Page<TagDto>> search(@RequestParam String q) {
    Pageable page = PageRequest.of(0, 10);
    return ResponseEntity.ok(tagRepository.findByNameIgnoreCaseContains(q, page));
  }

  @GetMapping("/trending")
  public ResponseEntity<List<TagDto>> getTrendingTags() {
    return ResponseEntity.ok(tagRepository.findTrendingTags());
  }
}