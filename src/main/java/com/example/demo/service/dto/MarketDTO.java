package com.example.demo.service.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Class DTO for the {@link com.example.demo.domain.Market} entity
 */
@Data
public class MarketDTO implements Serializable {

    private Long id;

    @NotBlank
    private String name;

    private Boolean activated;

    public MarketDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public MarketDTO setName(String name) {
        this.name = name;
        return this;
    }

    public MarketDTO setActivated(Boolean activated) {
        this.activated = activated;
        return this;
    }
}
