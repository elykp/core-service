package com.elykp.coreservice.assets;

import com.elykp.coreservice.assets.domain.AssetRS;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AssetServiceImpl implements AssetService {

  private final RestTemplate restTemplate;

  private static final String BASE_URL = "http://asset-service";

  @Override
  public Map<String, AssetRS> getByPhotoId(String photoId) {
    final String url = BASE_URL + "/api/assets/" + photoId;
    ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

    return response.getBody();
  }
}
