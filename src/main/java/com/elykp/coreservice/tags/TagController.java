package com.elykp.coreservice.tags;

import com.elykp.coreservice.shared.query.PageQueryBuilder;
import com.elykp.coreservice.shared.query.PageQueryParams;
import com.elykp.coreservice.tags.domain.CreateTagRQ;
import com.elykp.coreservice.tags.domain.TagRS;
import com.elykp.coreservice.tags.mapper.TagMapper;
import jakarta.validation.Valid;
import jakarta.ws.rs.BeanParam;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
  public ResponseEntity<Tag> create(@Valid @RequestBody CreateTagRQ createTagRQ) {
    try {
      Tag tag = new Tag();
      tag.setName(createTagRQ.getName());
      return new ResponseEntity<>(tagRepository.save(tag), HttpStatus.CREATED);
    } catch (DataIntegrityViolationException ex) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Tag already exists");
    }
  }

  @GetMapping
  public ResponseEntity<Page<TagRS>> search(@BeanParam PageQueryParams pageQueryParams) {
    Page<Tag> results;
    String query = pageQueryParams.getQ();
    PageRequest pageRequest = PageQueryBuilder.of(pageQueryParams);
    if (query == null || query.isEmpty()) {
      results = tagRepository.findAll(pageRequest);
    } else {
      results = tagRepository.findByNameIgnoreCaseContains(query, pageRequest);
    }
    return ResponseEntity.ok(results.map(TagMapper.INSTANCE::mapTagToTagRS));
  }

  @GetMapping("/trending")
  public ResponseEntity<List<TagRS>> getTrendingTags() {
    return ResponseEntity.ok(tagRepository.findTrendingTags());
  }
}