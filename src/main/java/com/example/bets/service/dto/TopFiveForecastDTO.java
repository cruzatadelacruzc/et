package com.example.bets.service.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
public class TopFiveForecastDTO extends ForecastDTO {

    @NotEmpty
    private Set<Long> golfers;

}
