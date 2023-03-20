package com.elykp.coreservice.users.domain;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserAttributeRQ {
  private Map<String, String> attributes;
}
