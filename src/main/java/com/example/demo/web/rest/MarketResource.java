package com.example.demo.web.rest;

import com.example.demo.security.AuthoritiesConstants;
import com.example.demo.service.MarketService;
import com.example.demo.service.dto.MarketDTO;
import com.example.demo.web.rest.error.BadRequestAlertException;
import com.example.demo.web.rest.util.HeaderUtil;
import com.example.demo.web.rest.util.PaginationUtil;
import com.example.demo.web.rest.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MarketResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(MarketResource.class);
    private final MarketService marketService;
    private final String ENTITY_NAME = "betMarket";
    @Value("${application.clientApp.name}")
    private String applicationName;

    public MarketResource(MarketService marketService) {
        this.marketService = marketService;
    }

    /**
     * {@code GET /markets/:id } get :id market.
     *
     * @param id the id of the marketDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the marketDTO,
     * or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/markets/{id}")
    public ResponseEntity<MarketDTO> getMarket(@PathVariable Long id) {
        LOGGER.debug("Rest request to get Market with ID: {}", id);
        return ResponseUtil.wrapOrNotFound(marketService.findOneById(id));
    }

    /**
     * {@code GET /market } : get all market
     *
     * @param pageable the pagination information
     * @return the {@link ResponseEntity} with a list of market in body and status {@code 200 (Ok)}
     */
    @GetMapping("/markets")
    public ResponseEntity<List<MarketDTO>> getAllMarket(Pageable pageable) {
        LOGGER.debug("Rest request to get all Market");
        Page<MarketDTO> result = marketService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHeaders(
                ServletUriComponentsBuilder.fromCurrentRequest(), result);
        return ResponseEntity.ok().headers(headers).body(result.getContent());
    }

    /**
     * {@code POST /market} create a new market
     *
     * @param marketDTO to create a market
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with MarketDTO in body
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/markets")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<MarketDTO> createMarket(@Valid @RequestBody MarketDTO marketDTO) throws URISyntaxException {
        LOGGER.debug("Rest request to create a Market with: {}", marketDTO);
        if (marketDTO.getId() != null) {
            throw new BadRequestAlertException("A new Market cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MarketDTO newMarket = marketService.createMarket(marketDTO);
        return ResponseEntity
                .created(new URI("/api/markets/" + newMarket.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(
                        applicationName, true, ENTITY_NAME, newMarket.getId().toString())
                        )
                .build();
    }

    /**
     * {@code DELETE /market/:id} delete a "id" market
     *
     * @param id the id of the marketDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (Not Content)}
     */
    @DeleteMapping("/markets/{id}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteMarket(@PathVariable Long id) {
        LOGGER.debug("Rest request to delete market with ID: {}", id);
        marketService.deleteMarket(id);
        return ResponseEntity.noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, ENTITY_NAME, id.toString()))
                .build();
    }

}
