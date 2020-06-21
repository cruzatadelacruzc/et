package com.example.demo.repository;

import com.example.demo.domain.Forecast;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForecastRepository extends JpaRepository<Forecast, Long> {

    @EntityGraph(attributePaths = "golfersForecasts")
    Optional<Forecast> findOneWithGolfersForecastsById(Long id);
}
