package com.example.bets.domain;

import com.example.bets.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MarketTest {
    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Market.class);
        Market market = new Market();
        market.setId(1L);
        Market market2 = new Market();
        market2.setId(market.getId());
        assertThat(market).isEqualTo(market2);
        market2.setId(2L);
        assertThat(market).isNotEqualTo(market2);
        market.setId(null);
        assertThat(market).isNotEqualTo(market2);
    }
}
