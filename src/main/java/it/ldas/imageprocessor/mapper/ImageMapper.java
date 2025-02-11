package it.ldas.imageprocessor.mapper;

import it.ldas.imageprocessor.model.entity.Image;
import it.ldas.imageprocessor.model.response.GetImageResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ImageMapper {

    @Mapping(target = "metadata", expression = "java(model.getMetadataMap())")
    GetImageResponse toResponse(Image model);

}
