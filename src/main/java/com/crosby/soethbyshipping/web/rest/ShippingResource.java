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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ShippingResource {

    private final Logger log = LoggerFactory.getLogger(ShippingResource.class);

    private static final String ENTITY_NAME = "soethbyShipping";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuoteService quoteService;
    private final ShipmentService shipmentService;

    //unified resource because no point in having split purpose when they're interacting constantly
    public ShippingResource(QuoteService quoteService, ShipmentService shipmentService) {
        this.quoteService = quoteService;
        this.shipmentService = shipmentService;
    }


    /**
     * {@code POST  /shipments/:id} : Create a new quote. Booking a shipment
     *
     * @param id the quote to persist.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new quoteDTO, or with status {@code 400 (Bad Request)} if the quote has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/shipments/:id")
    public ResponseEntity<Boolean> persistQuote(@PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to save Shipment : {}", id);

        boolean result = quoteService.persist(id);
        if(result) {
            return ResponseEntity.created(new URI("/api/shipments/" + id))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, String.valueOf(id)))
                .body(true);
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * {@code DELETE  /quotes/:id} : delete the "id" quote. Removing booked shipment
     *
     * @param id the id of the quoteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/shipments/:id")
    public ResponseEntity<Void> deleteQuoteChain(@PathVariable Long id) {
        log.debug("REST request to delete Quote and associated elements : {}", id);
        quoteService.deleteChain(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code GET  /shipments/:id} : get the "id" shipment and all of it's quotes.
     *
     * @param id the id of the shipmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shipmentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/shipments/{id}")
    public ResponseEntity<Shipment> getShipment(@PathVariable Long id) {
        log.debug("REST request to get Shipment : {}", id);
        Optional<Shipment> shipment = shipmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shipment);
    }

    @PostMapping("/shipments/requestQuotes")
    public ResponseEntity<List<Quote>> getQuotes(@RequestBody Shipment shipment){
        log.debug("REST request to get Quotes for shipment");
        shipmentService.save(shipment);
        return ResponseEntity.ok().body(quoteService.getQuotesFromProviders(shipment));
    }

}
