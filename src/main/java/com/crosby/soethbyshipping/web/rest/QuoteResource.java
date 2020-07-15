package com.crosby.soethbyshipping.web.rest;

import com.crosby.soethbyshipping.domain.Quote;
import com.crosby.soethbyshipping.domain.Shipment;
import com.crosby.soethbyshipping.service.QuoteService;
import com.crosby.soethbyshipping.service.ShipmentService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
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
    private final ShipmentService shipmentService;

    public QuoteResource(QuoteService quoteService, ShipmentService shipmentService) {
        this.quoteService = quoteService;
        this.shipmentService = shipmentService;
    }

    /**
     * {@code GET  /quote/:id} : get the "id" quote.
     *
     * @param id the id of the quote to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the quote, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/quote/{id}")
    public ResponseEntity<Quote> getQuote(@PathVariable Long id) {
        log.debug("REST request to get Quote : {}", id);
        Optional<Quote> quote = quoteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(quote);
    }


    /**
     * {@code DELETE  /quote/:id} : delete the "id" quote. Removing booked shipment
     *
     * @param id the id of the quote to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/quote/{id}")
    public ResponseEntity<Void> deleteQuoteChain(@PathVariable Long id) {
        log.debug("REST request to delete Quote and associated elements : {}", id);
        quoteService.deleteChain(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code PUT  /quote/persist/:id} : Create a new quote. Booking a shipment
     *
     * @param id the quote to persist.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the id of the persisted quote, or with status {@code 400 (Bad Request)} if the quote does not exist.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/quote/persist/{id}")
    public ResponseEntity<Boolean> persistQuote(@PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to save Shipment : {}", id);

        boolean result = quoteService.persist(id);
        if(result) {
            return ResponseEntity.created(new URI("/api/shipments/" + id))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, String.valueOf(id)))
                .body(true);
        }
        return ResponseEntity.notFound().build();
    }



    @PostMapping("/quote/requestQuotes")
    public ResponseEntity<List<Quote>> getQuotes(@RequestBody Shipment shipment, @RequestParam(value="sortBy", required = false) String sortKey){
        log.debug("REST request to get Quotes for shipment");
        shipmentService.save(shipment);
        return ResponseEntity.ok().body(quoteService.getQuotesFromProviders(shipment, Sort.by(Sort.DEFAULT_DIRECTION, sortKey)));
    }
}
