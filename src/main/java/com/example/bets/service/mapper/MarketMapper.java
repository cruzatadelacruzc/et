package com.example.bets.service.mapper;

import com.example.bets.domain.Market;
import com.example.bets.service.dto.MarketDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MarketMapper extends EntityMapper<MarketDTO, Market> {

    default Market fromId(Long id) {
        if (id == null) {
            return null;
        }
        Market market = new Market();
        market.setId(id);
        return market;
    }
}
