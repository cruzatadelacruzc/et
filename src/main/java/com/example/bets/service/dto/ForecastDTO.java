package com.example.bets.service.dto;

import com.example.bets.domain.Forecast;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Class DTO parent for {@link Forecast} entity
 */
public class ForecastDTO {

    private Long id;

    private LocalDateTime date;

    @NotNull
    private Long tournament;

    private Long round;

    @NotNull
    private Long forecaster;

    @DecimalMin(value = "0.0")
    private Double moneyToBet;

    @NotNull
    private Long marketId;


    public Long getId() {
        return id;
    }

    public ForecastDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public ForecastDTO setDate(LocalDateTime date) {
        this.date = date;
        return this;
    }

    public Long getTournament() {
        return tournament;
    }

    public ForecastDTO setTournament(Long tournament) {
        this.tournament = tournament;
        return this;
    }

    public Long getRound() {
        return round;
    }

    public ForecastDTO setRound(Long round) {
        this.round = round;
        return this;
    }

    public Long getForecaster() {
        return forecaster;
    }

    public ForecastDTO setForecaster(Long forecaster) {
        this.forecaster = forecaster;
        return this;
    }

    public Long getMarketId() {
        return marketId;
    }

    public ForecastDTO setMarketId(Long marketId) {
        this.marketId = marketId;
        return this;
    }

    public Double getMoneyToBet() {
        return moneyToBet;
    }

    public ForecastDTO setMoneyToBet(Double moneyToBet) {
        this.moneyToBet = moneyToBet;
        return this;
    }

    @Override
    public String toString() {
        return "ForecastDTO{" +
                "id=" + id +
                ", date=" + date +
                ", tournament=" + tournament +
                ", round=" + round +
                ", forecaster=" + forecaster +
                ", moneyToBet=" + moneyToBet +
                ", marketId=" + marketId +
                '}';
    }
}
