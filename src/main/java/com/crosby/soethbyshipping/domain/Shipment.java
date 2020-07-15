package com.crosby.soethbyshipping.domain;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(unique = true)
    private Address destination;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(unique = true)
    private Address source;

    @OneToMany(mappedBy = "shipment", cascade = {CascadeType.ALL})
    private Set<Quote> quotes = new HashSet<>();

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

    public Address getDestination() {
        return destination;
    }

    public Shipment destination(Address address) {
        this.destination = address;
        return this;
    }

    public void setDestination(Address address) {
        this.destination = address;
    }

    public Address getSource() {
        return source;
    }

    public Shipment source(Address address) {
        this.source = address;
        return this;
    }

    public void setSource(Address address) {
        this.source = address;
    }

    public Set<Quote> getQuotes() {
        return quotes;
    }

    public Shipment quotes(Set<Quote> quotes) {
        this.quotes = quotes;
        return this;
    }

    public Shipment addQuote(Quote quote) {
        this.quotes.add(quote);
        quote.setShipment(this);
        return this;
    }

    public Shipment removeQuote(Quote quote) {
        this.quotes.remove(quote);
        quote.setShipment(null);
        return this;
    }

    public void setQuotes(Set<Quote> quotes) {
        this.quotes = quotes;
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
