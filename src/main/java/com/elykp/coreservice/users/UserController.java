package com.elykp.coreservice.users;

import com.elykp.coreservice.users.domain.KcUserRS;
import com.elykp.coreservice.users.domain.UpdateUserAttributeRQ;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {
  private final UserServiceImpl userService;

  public UserController(UserServiceImpl userService) {
    this.userService = userService;
  }

  @GetMapping("{userId}")
  public ResponseEntity<KcUserRS> getUserById(@PathVariable String userId) {
    KcUserRS userResponse = userService.getKcUserById(userId);
    if (userResponse != null) {
      return ResponseEntity.ok(userResponse);
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }
  }

  @PutMapping
  public ResponseEntity updateUserAttributes(JwtAuthenticationToken authentication,
      @RequestBody UpdateUserAttributeRQ updateUserAttributeRQ) {
    userService.updateUserAttributes(
        authentication.getTokenAttributes().get("sub").toString(), updateUserAttributeRQ,
        authentication.getToken().getTokenValue());
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
