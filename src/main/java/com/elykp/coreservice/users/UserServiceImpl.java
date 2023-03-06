package com.elykp.coreservice.users;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException.NotFound;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceImpl implements UserService {

  private final RestTemplate restTemplate;
  @Value("${oauth.authority}")
  private String authority;
  @Value("${oauth.client-id}")
  private String clientId;
  @Value("${oauth.client-secret}")
  private String clientSecret;
  @Value("${oauth.authority-api-url}")
  private String authorityApiUrl;

  public UserServiceImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public Optional<String> requestAccessTokenFromIS() throws Unauthorized {
    String url = authority + "/protocol/openid-connect/token";

    HttpHeaders httpHeaders = new HttpHeaders();

    httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("grant_type", "client_credentials");
    map.add("client_id", clientId);
    map.add("client_secret", clientSecret);

    HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<>(map, httpHeaders);
    ResponseEntity<AccessTokenExchangeResponse> response = restTemplate.exchange(url,
        HttpMethod.POST,
        body,
        AccessTokenExchangeResponse.class);

    return Optional.ofNullable(response.getBody())
        .map(AccessTokenExchangeResponse::getAccess_token);
  }

  @Override
  @Nullable
  @Cacheable(value = "user", key = "#userId", unless = "#result == null")
  public KcUserResponseDto getKcUserById(String userId) throws NotFound {
    Optional<String> accessToken = requestAccessTokenFromIS();
    if (accessToken.isEmpty()) {
      return null;
    }

    String url = authorityApiUrl + "/users/" + userId;

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setBearerAuth(accessToken.get());
    HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<>(null, httpHeaders);

    ResponseEntity<KcUserResponseDto> response = restTemplate.exchange(url, HttpMethod.GET, body,
        KcUserResponseDto.class);
    if (response.getStatusCode() == HttpStatus.OK) {
      return response.getBody();
    }

    return null;
  }
}
