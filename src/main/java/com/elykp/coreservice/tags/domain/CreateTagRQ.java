package com.elykp.coreservice.tags.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateTagRQ {
  @NotBlank
  private String name;
}