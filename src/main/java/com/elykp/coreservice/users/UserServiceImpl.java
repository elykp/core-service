package com.elykp.coreservice.users;

import com.elykp.coreservice.users.domain.KcUserRS;
import com.elykp.coreservice.users.domain.UpdateUserAttributeRQ;
import jakarta.ws.rs.BadRequestException;
import java.util.Map;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@Log4j2
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
  public Optional<String> requestAccessTokenFromIS() {
    String url = authority + "/protocol/openid-connect/token";

    HttpHeaders httpHeaders = new HttpHeaders();

    httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("grant_type", "client_credentials");
    map.add("client_id", clientId);
    map.add("client_secret", clientSecret);

    HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<>(map, httpHeaders);
    try {
      ResponseEntity<AccessTokenExchangeResponse> response = restTemplate.exchange(url,
          HttpMethod.POST,
          body,
          AccessTokenExchangeResponse.class);

      return Optional.ofNullable(response.getBody())
          .map(AccessTokenExchangeResponse::getAccess_token);
    } catch (RestClientException ex) {
      log.error(ex.getMessage());
      return Optional.empty();
    }
  }

  @Override
  @Nullable
  @Cacheable(value = "user", key = "#userId", unless = "#result == null")
  public KcUserRS getKcUserById(String userId) {
    Optional<String> accessToken = requestAccessTokenFromIS();
    if (accessToken.isEmpty()) {
      return null;
    }

    return getUserByIdWithToken(userId, accessToken.get());
  }

  @Override
  @CacheEvict(value = "user", key = "#userId")
  public void updateUserAttributes(String userId, UpdateUserAttributeRQ updateUserAttributeRQ,
      String accessToken) {
    KcUserRS userRS = getUserByIdWithToken(userId, accessToken);
    if (userRS == null) {
      throw new BadRequestException("User not found");
    }

    Map<String, String[]> userAttributes = userRS.getAttributes();
    for (String key : updateUserAttributeRQ.getAttributes().keySet()) {
      userAttributes.put(key, new String[]{updateUserAttributeRQ.getAttributes().get(key)});
    }

    final String url = authorityApiUrl + "/users/" + userId;
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setBearerAuth(accessToken);
    HttpEntity<UpdateUserAttributeRQ> body = new HttpEntity<>(updateUserAttributeRQ, httpHeaders);

    restTemplate.exchange(url, HttpMethod.PUT, body, Void.class);
  }

  @Nullable
  private KcUserRS getUserByIdWithToken(String userId, String accessToken) {
    final String url = authorityApiUrl + "/users/" + userId;

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setBearerAuth(accessToken);
    HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<>(null, httpHeaders);
    ResponseEntity<KcUserRS> response = restTemplate.exchange(url, HttpMethod.GET, body,
        KcUserRS.class);
    return response.getBody();
  }
}
