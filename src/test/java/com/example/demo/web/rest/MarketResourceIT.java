package com.example.demo.web.rest;

import com.example.demo.BetsApplication;
import com.example.demo.domain.Market;
import com.example.demo.service.MarketService;
import com.example.demo.service.dto.MarketDTO;
import com.example.demo.service.mapper.MarketMapper;
import com.example.demo.web.rest.error.ExceptionTranslator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the {@link MarketResource} REST controller.
 */
@SpringBootTest(classes = BetsApplication.class)
public class MarketResourceIT {

    private static final String DEFAULT_NAME = "ABC";
    private static final String UPDATED_NAME = "DEF";

    private static final boolean DEFAULT_ACTIVE = true;
    private static final boolean UPDATED_ACTIVE = false;

    private MockMvc restMockMvc;

    private Market market;

    @Autowired
    private MarketMapper marketMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    @Autowired
    private MarketService marketService;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        final MarketResource marketResource = new MarketResource(marketService);
        this.restMockMvc = MockMvcBuilders.standaloneSetup(marketResource)
                .setValidator(validator)
                .setControllerAdvice(exceptionTranslator)
                .setMessageConverters(jacksonMessageConverter)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .build();
    }

    private static Market createEntity() {
        Market market = new Market();
        market.setName(DEFAULT_NAME);
        market.setActivated(DEFAULT_ACTIVE);
        return market;
    }

    @BeforeEach
    public void initTest() {
        this.market = createEntity();
    }

    @Test
    @Transactional
    public void createMarket() throws Exception {
        int databaseSizeInitial = TestUtil.findAll(em, Market.class).size();

        MarketDTO marketDTO = marketMapper.toDto(this.market);
        this.restMockMvc.perform(post("/api/markets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(marketDTO)))
                .andExpect(status().isCreated());

        // Validate the Poor in the database
        List<Market> all = TestUtil.findAll(em, Market.class);
        assertThat(all).hasSize(databaseSizeInitial + 1);
        Market testMarket = all.get(all.size() - 1);
        assertThat(testMarket.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMarket.getActivated()).isEqualTo(DEFAULT_ACTIVE);
    }
}
