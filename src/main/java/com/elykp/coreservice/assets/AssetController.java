package com.elykp.coreservice.assets;

import com.elykp.coreservice.assets.dto.AssetRS;
import com.elykp.coreservice.assets.mapper.AssetMapper;
import java.util.List;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/assets")
public class AssetController {

  private final AssetRepository assetRepository;

  @GetMapping
  List<AssetRS> getAll() {
    return StreamSupport.stream(assetRepository.findAll().spliterator(), false)
        .map(AssetMapper.INSTANCE::mapAssetToAssetRS).toList();
  }
}
