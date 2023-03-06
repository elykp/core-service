package com.elykp.coreservice.users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;

@Getter
public class KcUserResponseDto {
  private String id;
  private Long createdTimestamp;
  private String username;
  private Boolean enabled;
  private Boolean emailVerified;
  private Boolean totp;
  private String firstName;
  private String lastName;
  private String email;
  private Map<String, List<String>> attributes = new HashMap<>();
}
