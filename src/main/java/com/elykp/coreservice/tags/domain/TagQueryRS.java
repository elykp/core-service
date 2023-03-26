package com.elykp.coreservice.tags.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagQueryRS extends TagRS {

  private String highlighted;
  private long count;

  public TagQueryRS() {
    super();
  }

  public TagQueryRS(Long id, String name, long count, String highlighted) {
    super(id, name);
    this.count = count;
    this.highlighted = highlighted;
  }
}
