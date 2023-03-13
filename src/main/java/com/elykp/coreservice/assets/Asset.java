package com.elykp.coreservice.assets;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Asset {

  private Long id;

  private String ownerId;

  private String photoId;

  private String url;

  private Integer width;

  private Integer height;

  private String responsiveKey;

  private Long createdAt;
}
