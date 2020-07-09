package com.crosby.soethbyshipping.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.crosby.soethbyshipping.domain.Quote} entity.
 */
public class QuoteDTO implements Serializable {
    
    private Long id;

    private String provider;

    private Float quote;

    private Integer duration;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Float getQuote() {
        return quote;
    }

    public void setQuote(Float quote) {
        this.quote = quote;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuoteDTO)) {
            return false;
        }

        return id != null && id.equals(((QuoteDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuoteDTO{" +
            "id=" + getId() +
            ", provider='" + getProvider() + "'" +
            ", quote=" + getQuote() +
            ", duration=" + getDuration() +
            "}";
    }
}
