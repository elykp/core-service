package com.elykp.coreservice.assets;

import com.elykp.coreservice.photos.Photo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "asset_entity")
public class Asset {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 36)
  private String ownerId;

  @Column(columnDefinition = "varchar(255) default ''")
  private String url;

  @Column(nullable = false)
  private Integer width;

  @Column(nullable = false)
  private Integer height;

  @Column(name = "responsive_key", nullable = false, length = 20)
  private String responsiveKey;

  @Column(name = "created_at")
  private Long createdAt;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "photo_id", referencedColumnName = "id")
  private Photo photo;

  @PrePersist
  protected void prePersist() {
    if (this.createdAt == null) {
      createdAt = Instant.now().getEpochSecond();
    }
  }
}
