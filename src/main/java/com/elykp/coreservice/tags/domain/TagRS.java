package com.elykp.coreservice.tags.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagRS {

  private Long id;
  private String name;

  public TagRS() {
  }

  public TagRS(Long id, String name) {
    this.id = id;
    this.name = name;
  }
}