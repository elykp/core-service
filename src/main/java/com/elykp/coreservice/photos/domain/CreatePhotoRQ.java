package com.elykp.coreservice.photos.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class CreatePhotoRQ {
  @NotBlank(message = "Title cannot be blank")
  private String title;

  private Boolean nsfw;

  @NotBlank(message = "Blurhash cannot be empty")
  private String blurhash;

  @NotNull(message = "Tag list cannot be null")
  private final List<String> tags = new ArrayList<>();
}