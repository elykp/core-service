package com.elykp.coreservice.tags;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TagRepository extends JpaRepository<Tag, Long> {
  Page<TagDto> findByNameIgnoreCaseContains(String name, Pageable pageable);

  @Query(nativeQuery = true)
  List<TagDto> findTrendingTags();

  boolean existsByNameIgnoreCase(String name);

  Optional<Tag> findByNameIgnoreCase(String name);
}
