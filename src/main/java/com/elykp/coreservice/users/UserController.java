package com.elykp.coreservice.users;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.NotFound;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {
  private final UserServiceImpl userService;

  public UserController(UserServiceImpl userService) {
    this.userService = userService;
  }

  @GetMapping("{userId}")
  public ResponseEntity<KcUserResponseDto> getUserById(@PathVariable String userId) {
    try {
      KcUserResponseDto userResponse = userService.getKcUserById(userId);
      return ResponseEntity.ok(userResponse);
    } catch (NotFound e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }
  }
}
