package com.crosby.soethbyshipping.service;

import com.crosby.soethbyshipping.domain.Quote;
import com.crosby.soethbyshipping.domain.Shipment;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.crosby.soethbyshipping.domain.Quote}.
 */
public interface QuoteService {

    /**
     * Save a quote.
     *
     * @param quote the entity to save.
     * @return the persisted entity.
     */
    Quote save(Quote quote);

    /**
     * Get all the quotes.
     *
     * @return the list of entities.
     */
    List<Quote> findAll();


    /**
     * Get the "id" quote.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Quote> findOne(Long id);

    /**
     * Delete the "id" quote.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    boolean persist(Long id);

    void deleteChain(Long id);

    List<Quote> getQuotesFromProviders(Shipment shipment);
}
