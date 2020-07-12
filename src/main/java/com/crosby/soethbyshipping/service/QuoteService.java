package com.crosby.soethbyshipping.service;

import com.crosby.soethbyshipping.service.dto.QuoteDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.crosby.soethbyshipping.domain.Quote}.
 */
public interface QuoteService {

    /**
     * Save a quote.
     *
     * @param quoteDTO the entity to save.
     * @return the persisted entity.
     */
    QuoteDTO save(QuoteDTO quoteDTO);

    /**
     * Get all the quotes.
     *
     * @return the list of entities.
     */
    List<QuoteDTO> findAll();


    /**
     * Get the "id" quote.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<QuoteDTO> findOne(Long id);

    /**
     * Delete the "id" quote.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    boolean persist(Long id);

    void deleteChain(Long id);
}
