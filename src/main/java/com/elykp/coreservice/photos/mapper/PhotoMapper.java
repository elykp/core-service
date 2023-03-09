package com.elykp.coreservice.photos.mapper;

import com.elykp.coreservice.assets.Asset;
import com.elykp.coreservice.assets.dto.AssetRS;
import com.elykp.coreservice.assets.mapper.AssetMapper;
import com.elykp.coreservice.photos.Photo;
import com.elykp.coreservice.photos.dto.PhotoRS;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
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
public interface PhotoMapper {

  PhotoMapper INSTANCE = Mappers.getMapper(PhotoMapper.class);

  @Named("setToMap")
  static Map<String, AssetRS> setToMap(Set<Asset> assets) {
    return assets.stream().collect(
        Collectors.toMap(Asset::getResponsiveKey, AssetMapper.INSTANCE::mapAssetToAssetRS));
  }

  @Mapping(target = "assets", qualifiedByName = "setToMap")
  PhotoRS mapPhotoToPhotoRS(Photo photo);
}
