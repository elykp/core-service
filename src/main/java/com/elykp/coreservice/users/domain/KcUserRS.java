package com.elykp.coreservice.users.domain;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KcUserRS {
  private String id;
  private Long createdTimestamp;
  private String username;
  private Boolean enabled;
  private Boolean emailVerified;
  private Boolean totp;
  private String firstName;
  private String lastName;
  private String email;
  private Map<String, String[]> attributes = new HashMap<>();
}
