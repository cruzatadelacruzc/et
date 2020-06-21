package com.example.demo.service.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@ToString
public class TopFiveForecastDTO extends ForecastDTO {

    @NotEmpty
    private Set<Long> golfers;

}
