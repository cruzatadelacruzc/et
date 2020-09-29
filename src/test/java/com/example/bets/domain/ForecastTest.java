package com.example.bets.domain;

import com.example.bets.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ForecastTest {
    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Forecast.class);
        Forecast forecast = new Forecast();
        forecast.setId(1L);
        Forecast forecast2 = new Forecast();
        forecast2.setId(forecast.getId());
        assertThat(forecast).isEqualTo(forecast2);
        forecast2.setId(2L);
        assertThat(forecast).isNotEqualTo(forecast2);
        forecast.setId(null);
        assertThat(forecast).isNotEqualTo(forecast2);
    }
}
