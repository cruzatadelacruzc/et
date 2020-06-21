package com.example.demo.service.mapper;

import com.example.demo.domain.Market;
import com.example.demo.service.dto.MarketDTO;
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
