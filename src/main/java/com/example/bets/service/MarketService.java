package com.example.bets.service;

import com.example.bets.domain.Market;
import com.example.bets.repository.MarketRepository;
import com.example.bets.service.dto.MarketDTO;
import com.example.bets.service.mapper.MarketMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Class business logic of the {@link Market}
 */
@Slf4j
@Service
@Transactional
public class MarketService {
    private final MarketMapper marketMapper;
    private final MarketRepository marketRepository;
    private static final String MARKET_BY_ID = "marketById";

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
    @Cacheable(cacheNames = MARKET_BY_ID)
    public Optional<MarketDTO> findOneById(Long id) {
        log.debug("Request to get a Market with ID: {}", id);
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
        log.debug("Request to get all Market");
        return marketRepository.findAll(pageable).map(marketMapper::toDto);
    }

    /**
     * Save a market
     *
     * @param marketDTO the entity to save.
     * @return the persisted entity.
     */
    @CacheEvict(cacheNames = MARKET_BY_ID, allEntries = true)
    public MarketDTO createMarket(MarketDTO marketDTO) {
        log.debug("Request to save Market : {}", marketDTO);
        Market newMarket = marketMapper.toEntity(marketDTO);
        newMarket = marketRepository.save(newMarket);
        return marketMapper.toDto(newMarket);
    }

    /**
     * Delete a Market
     *
     * @param id identifier of the {@link Market}
     */
    @CacheEvict(cacheNames = MARKET_BY_ID, allEntries = true)
    public void deleteMarket(Long id) {
        marketRepository.deleteById(id);
    }
}
