package com.elykp.coreservice.photos.mapper;

import com.elykp.coreservice.photos.Photo;
import com.elykp.coreservice.photos.domain.PhotoRS;
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
public interface PhotoMapper {

  PhotoMapper INSTANCE = Mappers.getMapper(PhotoMapper.class);

  PhotoRS mapPhotoToPhotoRS(Photo photo);
}
