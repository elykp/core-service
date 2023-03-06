package com.elykp.coreservice.photos;

import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class CreatePhotoDto {
  @NotBlank(message = "Title cannot be blank")
  private String title;

  private Boolean nsfw;

  @NotBlank(message = "Blurhash cannot be empty")
  private String blurhash;

  private List<String> tags = new ArrayList<>();
}