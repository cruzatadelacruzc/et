package com.example.bets.web.rest;

import com.example.bets.service.TopFiveForecastService;
import com.example.bets.service.dto.TopFiveForecastDTO;
import com.example.bets.web.rest.error.BadRequestAlertException;
import com.example.bets.web.rest.util.HeaderUtil;
import com.example.bets.web.rest.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@RestController
@RequestMapping("/api")
public class TopFiveForecastResource {


    private final TopFiveForecastService topFiveForecastService;
    private final String ENTITY_NAME = "bet.TopFiveForecast";
    @Value("${application.clientApp.name}")
    private String applicationName;

    public TopFiveForecastResource(TopFiveForecastService topFiveForecastService) {
        this.topFiveForecastService = topFiveForecastService;
    }

    /**
     * {@code GET /topfiveforecasts/:id} get "id" TopFiveForecast
     *
     * @param id the id of the topfiveforecastDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the topfiveforecastDTO,
     * or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/topfiveforecasts/{id}")
    public ResponseEntity<TopFiveForecastDTO> getTopFiveForecast(@PathVariable Long id) {
        log.debug("Rest request to get a TopFiveForeCast wit ID: {}", id);
        return ResponseUtil.wrapOrNotFound(topFiveForecastService.findOneById(id));
    }

    /**
     * {@code POST /topfiveforecasts} create a new TopFiveForecast
     *
     * @param dto to create a TopFiveForecast
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with TopFiveForecastDTO in body
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/topfiveforecasts")
    public ResponseEntity<TopFiveForecastDTO> createTopFiveForecast(@Valid @RequestBody TopFiveForecastDTO dto) throws URISyntaxException {
        log.debug("Rest request to create a TopFiveForeCast with: {}", dto);
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new TopFiveForecast cannot already have an ID", ENTITY_NAME, "idexists");
        }

        TopFiveForecastDTO topFiveForecastDTO = topFiveForecastService.createTopFiveForecast(dto);
        return ResponseEntity.created(new URI("/api/topfiveforecasts/" + topFiveForecastDTO.getId().toString()))
                .headers(HeaderUtil.createEntityCreationAlert(
                        applicationName, true, ENTITY_NAME, topFiveForecastDTO.getId().toString()))
                .body(topFiveForecastDTO);
    }
}
