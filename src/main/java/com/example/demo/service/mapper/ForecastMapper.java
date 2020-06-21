package com.example.demo.service.mapper;

import com.example.demo.domain.Forecast;
import com.example.demo.service.dto.ForecastDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ForecastMapper extends EntityMapper<ForecastDTO, Forecast> {

    @Mapping(source = "market.id", target = "marketId")
    ForecastDTO toDto (Forecast forecast);

    @Mapping(source = "marketId", target = "market.id")
    Forecast toEntity (ForecastDTO forecastDTO);
}
