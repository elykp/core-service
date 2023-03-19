package com.elykp.coreservice.tags;

import com.elykp.coreservice.tags.domain.TagRS;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TagRepository extends JpaRepository<Tag, Long> {

  Page<Tag> findByNameIgnoreCaseContains(String name, Pageable pageable);

  @Query(nativeQuery = true)
  List<TagRS> findTrendingTags();

  Optional<Tag> findByNameIgnoreCase(String name);
}
