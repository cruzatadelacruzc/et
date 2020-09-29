package com.example.bets.service;

import com.example.bets.domain.Forecast;
import com.example.bets.domain.GolfersForecast;
import com.example.bets.repository.ForecastRepository;
import com.example.bets.security.SecurityUtils;
import com.example.bets.service.dto.TopFiveForecastDTO;
import com.example.bets.service.error.GolfersListEmptyException;
import com.example.bets.service.error.MoneyNoSufficientException;
import com.example.bets.service.feign.RoundServiceFeign;
import com.example.bets.service.feign.WalletServiceFeign;
import com.example.bets.service.feign.dto.RoundDTO;
import com.example.bets.service.feign.dto.WalletDTO;
import com.example.bets.service.mapper.TopFiveForecastMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Class business logic of the {@link GolfersForecast}
 */
@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class TopFiveForecastService {

    private final TopFiveForecastMapper topFiveForecastMapper;

    private final ForecastRepository forecastRepository;

    private final WalletServiceFeign walletServiceFeign;

    private final RoundServiceFeign roundServiceFeign;

    private static final String ONE_BY_ID = "findOneById";

    /**
     * Get a {@link GolfersForecast}
     *
     * @param id identifier of the {@link GolfersForecast}
     * @return {@link TopFiveForecastDTO} instance if is present.
     */
    @Transactional(readOnly = true)
    @Cacheable(value = ONE_BY_ID)
    public Optional<TopFiveForecastDTO> findOneById(Long id) {
        log.debug("Request to get a TopFiveForecast with ID: {}", id);
        return forecastRepository.findOneWithGolfersForecastsById(id).map(topFiveForecastMapper::toDto);
    }

    /**
     * Create a new TopFive forecast
     *
     * @param topFiveForecastDTO the entity to save.
     * @return the persisted entity.
     */
    @CacheEvict(cacheNames = ONE_BY_ID, allEntries = true)
    public TopFiveForecastDTO createTopFiveForecast(TopFiveForecastDTO topFiveForecastDTO) {
        if (topFiveForecastDTO.getGolfers().isEmpty()) {
            throw new GolfersListEmptyException();
        }
        String usernameBetter = SecurityUtils
                .getCurrentUserLogin()
                .orElseThrow(() -> new NullPointerException("No current user"));

        ResponseEntity<WalletDTO> walletDTO = walletServiceFeign.getWalletByUsername(usernameBetter);
        if (walletDTO.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new NoSuchElementException(String.format("Wallet for current user %s", usernameBetter));
        }

        WalletDTO wallet = walletDTO.getBody();
        if (wallet.getTotal_amount() < topFiveForecastDTO.getMoneyToBet()) {
            throw new MoneyNoSufficientException();
        }

        ResponseEntity<RoundDTO> roundDTOResponseEntity = roundServiceFeign.getRoundById(topFiveForecastDTO.getRound());
        if (roundDTOResponseEntity.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new NoSuchElementException(String.format("Round by ID %s", topFiveForecastDTO.getRound()));
        }

        Forecast topFiveForecast = topFiveForecastMapper.toEntity(topFiveForecastDTO);
        topFiveForecast.setDate(LocalDateTime.now());
        forecastRepository.save(topFiveForecast);

        double amountRemaining = wallet.getTotal_amount() - topFiveForecastDTO.getMoneyToBet();
        wallet.setTotal_amount(amountRemaining);
        walletServiceFeign.updateAmountWalletByUser(wallet);

        log.debug("Created TopFiveForecast with: {}", topFiveForecast);
        return topFiveForecastMapper.toDto(topFiveForecast);
    }


}
