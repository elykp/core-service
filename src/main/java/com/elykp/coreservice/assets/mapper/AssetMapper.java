package com.elykp.coreservice.assets.mapper;

import com.elykp.coreservice.assets.Asset;
import com.elykp.coreservice.assets.domain.AssetRS;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
    unmappedSourcePolicy = ReportingPolicy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT
)
public interface AssetMapper {
  AssetMapper INSTANCE = Mappers.getMapper(AssetMapper.class);

  AssetRS mapAssetToAssetRS(Asset asset);
}
