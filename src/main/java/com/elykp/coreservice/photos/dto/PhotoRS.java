package com.elykp.coreservice.photos.dto;

import com.elykp.coreservice.assets.dto.AssetRS;
import com.elykp.coreservice.tags.Tag;
import java.util.Map;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhotoRS {
  private String id;
  private String title;
  private String creatorId;
  private Integer likeCount;
  private Boolean nsfw;
  private Long createdAt;
  private Set<Tag> tags;
  private Map<String, AssetRS> assets;
}
