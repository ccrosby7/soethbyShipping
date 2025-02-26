package com.crosby.soethbyshipping.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Quote.
 */
@Entity
@Table(name = "quote")
public class Quote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "provider")
    private String provider;

    @Column(name = "price")
    private Float price;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "persist")
    private boolean persist;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JsonIgnoreProperties(value = "quotes", allowSetters = true)
    private Shipment shipment;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProvider() {
        return provider;
    }

    public Quote provider(String provider) {
        this.provider = provider;
        return this;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Float getPrice() {
        return price;
    }

    public Quote price(Float quote) {
        this.price = quote;
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public Quote duration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public Quote shipment(Shipment shipment) {
        this.shipment = shipment;
        return this;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    public Quote persist(boolean persist){
        this.persist = persist;
        return this;
    }

    public boolean isPersist() {
        return persist;
    }

    public void setPersist(boolean persist) {
        this.persist = persist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Quote)) {
            return false;
        }
        return id != null && id.equals(((Quote) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Quote{" +
            "id=" + getId() +
            ", provider='" + getProvider() + "'" +
            ", quote=" + getPrice() +
            ", duration=" + getDuration() +
            "}";
    }


}
