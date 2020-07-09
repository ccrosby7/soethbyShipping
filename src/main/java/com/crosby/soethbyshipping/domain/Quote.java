package com.crosby.soethbyshipping.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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

    @Column(name = "quote")
    private Float quote;

    @Column(name = "duration")
    private Integer duration;

    @OneToMany(mappedBy = "quote")
    private Set<Shipment> shipments = new HashSet<>();

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

    public Float getQuote() {
        return quote;
    }

    public Quote quote(Float quote) {
        this.quote = quote;
        return this;
    }

    public void setQuote(Float quote) {
        this.quote = quote;
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

    public Set<Shipment> getShipments() {
        return shipments;
    }

    public Quote shipments(Set<Shipment> shipments) {
        this.shipments = shipments;
        return this;
    }

    public Quote addShipment(Shipment shipment) {
        this.shipments.add(shipment);
        shipment.setQuote(this);
        return this;
    }

    public Quote removeShipment(Shipment shipment) {
        this.shipments.remove(shipment);
        shipment.setQuote(null);
        return this;
    }

    public void setShipments(Set<Shipment> shipments) {
        this.shipments = shipments;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

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
            ", quote=" + getQuote() +
            ", duration=" + getDuration() +
            "}";
    }
}
