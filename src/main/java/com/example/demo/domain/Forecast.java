package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Class Forecast to save predictions for each bet
 */
@Data
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Forecast implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_bet", nullable = false)
    private LocalDateTime date;

    @Column(name = "tournament_id")
    private Long tournament;

    @Column(name = "round")
    private Long round;

    @Column(name = "forecaster_id")
    private Long forecaster;

    @Column(name = "money_to_bet")
    private Double moneyToBet;

    @ManyToOne
    @JsonIgnore
    private Market market;

    @OneToMany(mappedBy = "forecast")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<GolfersForecast> golfersForecasts = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Forecast)) {
            return false;
        }
        return id != null && id.equals(((Forecast) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Bet{" +
                "id=" + id +
                ", date=" + date +
                ", tournament=" + tournament +
                ", round=" + round +
                ", forecaster=" + forecaster +
                ", market=" + market +
                '}';
    }
}
