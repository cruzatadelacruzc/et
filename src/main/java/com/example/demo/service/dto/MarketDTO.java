package com.example.demo.service.dto;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

/**
 * Class DTO for the {@link com.example.demo.domain.Market} entity
 */
public class MarketDTO implements Serializable {

    private Long id;

    @NotBlank
    private String name;

    private Boolean activated;

    public Long getId() {
        return id;
    }

    public MarketDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public MarketDTO setName(String name) {
        this.name = name;
        return this;
    }

    public Boolean getActivated() {
        return activated;
    }

    public MarketDTO setActivated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MarketDTO)) return false;
        MarketDTO marketDTO = (MarketDTO) o;
        if (getId() == null || marketDTO.getId() == null) return false;
        return getId().equals(marketDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "MarketDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", activated=" + activated +
                '}';
    }
}
