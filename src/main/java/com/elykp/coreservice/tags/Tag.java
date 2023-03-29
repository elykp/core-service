package com.elykp.coreservice.tags;

import com.elykp.coreservice.photos.Photo;
import com.elykp.coreservice.tags.domain.TagQueryRS;
import com.elykp.coreservice.tags.domain.TagRS;
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
@NamedNativeQuery(
    name = "Tag.findTrendingTags",
    query = "SELECT dbo.tag_entity.id, dbo.tag_entity.name, COUNT(*) " +
        "FROM dbo.tag_entity " +
        "JOIN dbo.photo_tags ON photo_tags.tag_id = tag_entity.id " +
        "GROUP BY tag_entity.id, tag_entity.name " +
        "ORDER BY COUNT(*) DESC " +
        "LIMIT 10",
    resultSetMapping = "Mapping.TagRS"
)
@NamedNativeQuery(
    name = "Tag.findRelatedTagsByQueryIgnoreCaseContains",
    query = """
                SELECT dbo.tag_entity.id, dbo.tag_entity.name, COUNT(*), ts_headline('english',
                                                   dbo.tag_entity.name,
                                                   plainto_tsquery('english', :query),
                                                   'HighlightAll=true, StartSel=<strong>, StopSel=</strong>'
                                                  ) as highlighted
                FROM dbo.tag_entity
                JOIN dbo.photo_tags ON photo_tags.tag_id = tag_entity.id
                WHERE plainto_tsquery(:query) @@ to_tsvector(tag_entity.name)
                GROUP BY tag_entity.id, tag_entity.name
                ORDER BY COUNT(*) DESC
                LIMIT 10
        """,
    resultSetMapping = "Mapping.TagQueryRS"
)

@SqlResultSetMapping(name = "Mapping.TagRS",
    classes = @ConstructorResult(targetClass = TagRS.class,
        columns = {@ColumnResult(name = "id"),
            @ColumnResult(name = "name")
        }))
@SqlResultSetMapping(name = "Mapping.TagQueryRS",
    classes = @ConstructorResult(targetClass = TagQueryRS.class,
        columns = {@ColumnResult(name = "id"),
            @ColumnResult(name = "name"),
            @ColumnResult(name = "count"),
            @ColumnResult(name = "highlighted")
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