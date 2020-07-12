package com.crosby.soethbyshipping.web.rest;

import com.crosby.soethbyshipping.service.QuoteService;
import com.crosby.soethbyshipping.web.rest.errors.BadRequestAlertException;
import com.crosby.soethbyshipping.service.dto.QuoteDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.crosby.soethbyshipping.domain.Quote}.
 */
@RestController
@RequestMapping("/api")
public class QuoteResource {

    private final Logger log = LoggerFactory.getLogger(QuoteResource.class);

    private static final String ENTITY_NAME = "soethbyShippingQuote";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuoteService quoteService;

    public QuoteResource(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    /**
     * {@code POST  /quotes} : Create a new quote.
     *
     * @param id the quote to persist.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new quoteDTO, or with status {@code 400 (Bad Request)} if the quote has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/quotes")
    public ResponseEntity<Boolean> persistQuote(@RequestBody Long id) throws URISyntaxException {
        log.debug("REST request to save Shipment : {}", id);

        boolean result = quoteService.persist(id);
        if(result) {
            return ResponseEntity.created(new URI("/api/quote/" + id))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, String.valueOf(id)))
                .body(true);
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * {@code PUT  /quotes} : Updates an existing quote.
     *
     * @param quoteDTO the quoteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated quoteDTO,
     * or with status {@code 400 (Bad Request)} if the quoteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the quoteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/quotes")
    public ResponseEntity<QuoteDTO> updateQuote(@RequestBody QuoteDTO quoteDTO) throws URISyntaxException {
        log.debug("REST request to update Quote : {}", quoteDTO);
        if (quoteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QuoteDTO result = quoteService.save(quoteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, quoteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /quotes} : get all the quotes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of quotes in body.
     */
    @GetMapping("/quotes")
    public List<QuoteDTO> getAllQuotes() {
        log.debug("REST request to get all Quotes");
        return quoteService.findAll();
    }

    /**
     * {@code GET  /quotes/:id} : get the "id" quote.
     *
     * @param id the id of the quoteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the quoteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/quotes/{id}")
    public ResponseEntity<QuoteDTO> getQuote(@PathVariable Long id) {
        log.debug("REST request to get Quote : {}", id);
        Optional<QuoteDTO> quoteDTO = quoteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(quoteDTO);
    }

    /**
     * {@code DELETE  /quotes/:id} : delete the "id" quote.
     *
     * @param id the id of the quoteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/quotes/{id}")
    public ResponseEntity<Void> deleteQuoteChain(@PathVariable Long id) {
        log.debug("REST request to delete Quote and associated elements : {}", id);
        quoteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
