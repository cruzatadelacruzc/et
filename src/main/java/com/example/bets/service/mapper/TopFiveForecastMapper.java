package com.example.bets.service.mapper;

import com.example.bets.domain.Forecast;
import com.example.bets.domain.GolfersForecast;
import com.example.bets.service.dto.TopFiveForecastDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TopFiveForecastMapper implements EntityMapper<TopFiveForecastDTO, Forecast> {

    @Autowired
    private MarketMapper marketMapper;

    @Override
    public Forecast toEntity(TopFiveForecastDTO dto) {
        if (dto == null) {
            return null;
        }

        Forecast forecast = new Forecast();
        if (dto.getMarketId() != null) {
            forecast.setMarket(marketMapper.fromId(dto.getMarketId()));
        }
        forecast.setForecaster(dto.getForecaster());
        forecast.setMoneyToBet(dto.getMoneyToBet());
        forecast.setRound(dto.getRound());
        forecast.setTournament(dto.getTournament());
        Set<GolfersForecast> golferForecast = dto.getGolfers().
                stream()
                .filter(Objects::nonNull)
                .map(golfer_id -> {
                    GolfersForecast newGolferForecast = new GolfersForecast();
                    newGolferForecast.setGolfer(golfer_id);
                    return newGolferForecast;
                })
                .collect(Collectors.toSet());
        forecast.setGolfersForecasts(golferForecast);
        return forecast;
    }

    @Override
    public TopFiveForecastDTO toDto(Forecast entity) {
        if (entity == null) {
            return null;
        }

        TopFiveForecastDTO topFiveForecastDTO = new TopFiveForecastDTO();
        topFiveForecastDTO.setId(entity.getId());
        topFiveForecastDTO.setDate(entity.getDate());
        topFiveForecastDTO.setRound(entity.getRound());
        topFiveForecastDTO.setMoneyToBet(entity.getMoneyToBet());
        topFiveForecastDTO.setForecaster(entity.getForecaster());
        topFiveForecastDTO.setTournament(entity.getTournament());
        topFiveForecastDTO.setMarketId(entity.getMarket().getId());
        topFiveForecastDTO.setGolfers(entity.getGolfersForecasts()
                .stream()
                .map(GolfersForecast::getGolfer)
                .collect(Collectors.toSet()));
        return topFiveForecastDTO;
    }

    @Override
    public List<Forecast> toEntity(List<TopFiveForecastDTO> dtoList) {
        return null;
    }

    @Override
    public List<TopFiveForecastDTO> toDto(List<Forecast> entityList) {
        return null;
    }
}
