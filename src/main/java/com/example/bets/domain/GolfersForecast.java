package com.example.bets.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Class golfers forecast in TopFive and Live bet.
 */
@Data
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GolfersForecast implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "golfer_id")
    private Long golfer;

    @ManyToOne
    @JsonIgnoreProperties("golfersForecasts")
    private Forecast forecast;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GolfersForecast)) {
            return false;
        }
        return id != null && id.equals(((GolfersForecast) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "GolfersForecast{" +
                "id=" + id +
                ", golfer=" + golfer +
                '}';
    }
}
