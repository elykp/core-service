package com.elykp.coreservice.tags;

import com.elykp.coreservice.photos.Photo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@NamedNativeQuery(name = "Tag.findTrendingTags",
    query = "SELECT tag_entity.id, tag_entity.name, COUNT(*) " +
        "FROM tag_entity " +
        "JOIN photo_tags ON photo_tags.tag_id = tag_entity.id " +
        "GROUP BY tag_entity.id, tag_entity.name " +
        "ORDER BY COUNT(*) DESC " +
        "LIMIT 10",
    resultSetMapping = "Mapping.TagResponseDto")
@SqlResultSetMapping(name = "Mapping.TagResponseDto",
    classes = @ConstructorResult(targetClass = TagDto.class,
        columns = {@ColumnResult(name = "id"),
            @ColumnResult(name = "name")
        }))
@Table(name = "tag_entity", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name"}, name = "UNIQUE_NAME_CONSTRAINT")
}, indexes = {
    @Index(name = "IDX_NAME", columnList = "name")
})
public class Tag {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(length = 100, nullable = false)
  private String name;

  @Column(name = "created_at")
  private Long createdAt;

  @ManyToMany(mappedBy = "tags")
  @JsonIgnore
  private Set<Photo> photos = new HashSet<>();

  @PrePersist
  protected void prePersist() {
    if (this.createdAt == null) {
      createdAt = Instant.now().getEpochSecond();
    }
  }
}