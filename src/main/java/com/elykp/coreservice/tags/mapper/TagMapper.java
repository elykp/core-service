package com.elykp.coreservice.tags.mapper;

import com.elykp.coreservice.tags.Tag;
import com.elykp.coreservice.tags.domain.TagRS;
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
public interface TagMapper {
  TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

  TagRS mapTagToTagRS(Tag tag);
}
