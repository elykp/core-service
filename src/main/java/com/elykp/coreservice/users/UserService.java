package com.elykp.coreservice.users;

import java.util.Optional;
import org.springframework.web.client.HttpClientErrorException;

public interface UserService {

  Optional<String> requestAccessTokenFromIS() throws HttpClientErrorException.Unauthorized;

  KcUserResponseDto getKcUserById(String userId) throws HttpClientErrorException.NotFound;
}
