package com.crosby.soethbyshipping.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Shipment.
 */
@Entity
@Table(name = "shipment")
public class Shipment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "height")
    private Float height;

    @Column(name = "length")
    private Float length;

    @Column(name = "width")
    private Float width;

    @Column(name = "weight")
    private Float weight;

    @ManyToOne
    @JsonIgnoreProperties(value = "shipments", allowSetters = true)
    private Quote quote;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getHeight() {
        return height;
    }

    public Shipment height(Float height) {
        this.height = height;
        return this;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getLength() {
        return length;
    }

    public Shipment length(Float length) {
        this.length = length;
        return this;
    }

    public void setLength(Float length) {
        this.length = length;
    }

    public Float getWidth() {
        return width;
    }

    public Shipment width(Float width) {
        this.width = width;
        return this;
    }

    public void setWidth(Float width) {
        this.width = width;
    }

    public Float getWeight() {
        return weight;
    }

    public Shipment weight(Float weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Quote getQuote() {
        return quote;
    }

    public Shipment quote(Quote quote) {
        this.quote = quote;
        return this;
    }

    public void setQuote(Quote quote) {
        this.quote = quote;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Shipment)) {
            return false;
        }
        return id != null && id.equals(((Shipment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Shipment{" +
            "id=" + getId() +
            ", height=" + getHeight() +
            ", length=" + getLength() +
            ", width=" + getWidth() +
            ", weight=" + getWeight() +
            "}";
    }
}
