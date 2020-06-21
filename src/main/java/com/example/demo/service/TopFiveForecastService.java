package com.example.demo.service;

import com.example.demo.domain.GolfersForecast;
import com.example.demo.repository.ForecastRepository;
import com.example.demo.service.dto.TopFiveForecastDTO;
import com.example.demo.service.mapper.TopFiveForecastMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Class business logic of the {@link GolfersForecast}
 */
@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class TopFiveForecastService {

    private final TopFiveForecastMapper topFiveForecastMapper;

    private final ForecastRepository forecastRepository;

    /**
     * Get a {@link GolfersForecast}
     *
     * @param id identifier of the {@link GolfersForecast}
     * @return {@link TopFiveForecastDTO} instance if is present.
     */
    @Transactional(readOnly = true)
    public Optional<TopFiveForecastDTO> findOneById(Long id) {
        log.debug("Request to get a TopFiveForecast with ID: {}", id);
        return forecastRepository.findOneWithGolfersForecastsById(id).map(topFiveForecastMapper::toDto);
    }




}
