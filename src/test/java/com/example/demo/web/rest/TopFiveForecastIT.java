package com.example.demo.web.rest;

import com.example.demo.BetsApplication;
import com.example.demo.service.TopFiveForecastService;
import com.example.demo.web.rest.error.ExceptionTranslator;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@SpringBootTest(classes = BetsApplication.class)
public class TopFiveForecastIT {
    private static final String DEFAULT_NAME = "ABC";
    private static final String UPDATED_NAME = "DEF";

    private static final LocalDateTime DEFAULT_DATE = LocalDateTime.of(2020,5,20,9,30,0);
    private static final LocalDateTime UPDATED_DATE = LocalDateTime.of(1991,6,17,5,25,0);

    private static final Long DEFAULT_TOURNAMENT = 365L;
    private static final Long UPDATED_TOURNAMENT = 364L;

    private static final Long DEFAULT_ROUND = 4L;
    private static final Long UPDATED_ROUND = 3L;

    private static final Long DEFAULT_FORECASTER = 1425L;
    private static final Long UPDATED_FORECASTER = 1991L;

    private static final Double DEFAULT_MONEY_TO_BET = 10.5;
    private static final Double UPDATED_MONEY_TO_BET = 45.2;

    @Autowired
    private TopFiveForecastService topFiveForecastService;


    private MockMvc restMockMvc;

    @Autowired
    private Validator validator;

    @Autowired
    private EntityManager em;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    public void setUp(){
        MockitoAnnotations.initMocks(this);
        TopFiveForecastResource topFiveForecastResource = new TopFiveForecastResource(topFiveForecastService);
        restMockMvc = MockMvcBuilders.standaloneSetup(topFiveForecastResource)
                .setValidator(validator)
                .setControllerAdvice(exceptionTranslator)
                .setMessageConverters(jacksonMessageConverter)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .build();
    }

}
