package com.example.bets.service.dto;

import com.example.bets.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MarketDTOTest {
    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarketDTO.class);
        MarketDTO marketDTO = new MarketDTO();
        marketDTO.setId(1L);
        MarketDTO marketDTO2 = new MarketDTO();
        marketDTO2.setId(marketDTO.getId());
        assertThat(marketDTO).isEqualTo(marketDTO2);
        marketDTO2.setId(2L);
        assertThat(marketDTO).isNotEqualTo(marketDTO2);
        marketDTO.setId(null);
        assertThat(marketDTO2).isNotEqualTo(marketDTO);
    }
}
