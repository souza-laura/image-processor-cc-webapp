package it.ldas.imageprocessor.mapper;

import it.ldas.imageprocessor.model.entity.ImageStatistics;
import it.ldas.imageprocessor.model.response.StatsResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatisticsMapper {


    StatsResponse toResponse(ImageStatistics model);

}
