package com.elykp.coreservice.photos;

import com.elykp.coreservice.tags.Tag;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.Set;
import lombok.Data;

import java.time.Instant;
import java.util.HashSet;

@Data
@Entity
@Table(name = "photo_entity", uniqueConstraints = {
    @UniqueConstraint(name = "UNIQUE_ID_CONSTRAINT", columnNames = {"id"})
}, indexes = {
    @Index(name = "IDX_ID", columnList = "id"),
    @Index(name = "IDX_CREATOR_ID", columnList = "creator_id")
})
public class Photo {

  @Id
  @Column(nullable = false, length = 8)
  private String id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false, name = "creator_id", length = 36)
  private String creatorId;

  @Column(name = "like_count", columnDefinition = "integer default 0")
  private Integer likeCount;

  @Column(columnDefinition = "boolean default false")
  private Boolean nsfw;

  @Column(length = 30, nullable = false)
  private String blurhash;

  @Column(name = "created_at")
  private Long createdAt;

  @ManyToMany
  @JoinTable(
      name = "photo_tags",
      joinColumns = @JoinColumn(name = "photo_id", nullable = false, updatable = false),
      inverseJoinColumns = @JoinColumn(name = "tag_id", nullable = false)
  )
  private Set<Tag> tags = new HashSet<>();

  @PrePersist
  protected void prePersist() {
    if (this.createdAt == null) {
      createdAt = Instant.now().getEpochSecond();
    }
  }
}