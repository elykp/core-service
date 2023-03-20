package com.elykp.coreservice.assets;

import com.elykp.coreservice.assets.domain.AssetRS;
import java.util.Map;

public interface AssetService {
  Map<String, AssetRS> getByPhotoId(String id);
}
