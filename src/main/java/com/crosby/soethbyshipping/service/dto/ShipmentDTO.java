package com.crosby.soethbyshipping.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.crosby.soethbyshipping.domain.Shipment} entity.
 */
public class ShipmentDTO implements Serializable {
    
    private Long id;

    private Float height;

    private Float length;

    private Float width;

    private Float weight;


    private Long quoteId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getLength() {
        return length;
    }

    public void setLength(Float length) {
        this.length = length;
    }

    public Float getWidth() {
        return width;
    }

    public void setWidth(Float width) {
        this.width = width;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Long getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(Long quoteId) {
        this.quoteId = quoteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShipmentDTO)) {
            return false;
        }

        return id != null && id.equals(((ShipmentDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShipmentDTO{" +
            "id=" + getId() +
            ", height=" + getHeight() +
            ", length=" + getLength() +
            ", width=" + getWidth() +
            ", weight=" + getWeight() +
            ", quoteId=" + getQuoteId() +
            "}";
    }
}
