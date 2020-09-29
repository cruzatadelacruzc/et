package com.example.bets.domain;

import com.example.bets.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GolfersForecastTest {
    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GolfersForecast.class);
        GolfersForecast golfersForecast = new GolfersForecast();
        golfersForecast.setId(1L);
        GolfersForecast golfersForecast2 = new GolfersForecast();
        golfersForecast2.setId(golfersForecast.getId());
        assertThat(golfersForecast).isEqualTo(golfersForecast2);
        golfersForecast2.setId(2L);
        assertThat(golfersForecast).isNotEqualTo(golfersForecast2);
        golfersForecast.setId(null);
        assertThat(golfersForecast).isNotEqualTo(golfersForecast2);
    }
}
