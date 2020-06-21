package com.example.demo.service;

import com.example.demo.domain.Market;
import com.example.demo.repository.MarketRepository;
import com.example.demo.service.dto.MarketDTO;
import com.example.demo.service.mapper.MarketMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Class business logic of the {@link Market}
 */
@Service
@Transactional
public class MarketService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MarketService.class);
    private final MarketMapper marketMapper;
    private final MarketRepository marketRepository;

    public MarketService(MarketMapper marketMapper, MarketRepository marketRepository) {
        this.marketMapper = marketMapper;
        this.marketRepository = marketRepository;
    }

    /**
     * Get a {@link Market}
     *
     * @param id identifier of the {@link Market}
     * @return {@link MarketDTO} instance if is present.
     */
    @Transactional(readOnly = true)
    public Optional<MarketDTO> findOneById(Long id) {
        LOGGER.debug("Request to get a Market with ID: {}", id);
        return marketRepository.findById(id).map(marketMapper::toDto);
    }

    /**
     * Get all the {@link Market}.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MarketDTO> findAll(Pageable pageable) {
        LOGGER.debug("Request to get all Market");
        return marketRepository.findAll(pageable).map(marketMapper::toDto);
    }

    /**
     * Save a market
     *
     * @param marketDTO the entity to save.
     * @return the persisted entity.
     */
    public MarketDTO createMarket(MarketDTO marketDTO) {
        LOGGER.debug("Request to save Market : {}", marketDTO);
        Market newMarket = marketMapper.toEntity(marketDTO);
        newMarket = marketRepository.save(newMarket);
        return marketMapper.toDto(newMarket);
    }

    /**
     * Delete a Market
     *
     * @param id identifier of the {@link Market}
     */
    public void deleteMarket(Long id) {
        marketRepository.deleteById(id);
    }
}
