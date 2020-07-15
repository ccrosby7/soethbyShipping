package com.crosby.soethbyshipping.web.rest;

import com.crosby.soethbyshipping.RedisTestContainerExtension;
import com.crosby.soethbyshipping.SoethbyShippingApp;
import com.crosby.soethbyshipping.domain.Address;
import com.crosby.soethbyshipping.domain.Quote;
import com.crosby.soethbyshipping.domain.Shipment;
import com.crosby.soethbyshipping.repository.QuoteRepository;
import com.crosby.soethbyshipping.repository.ShipmentRepository;
import com.crosby.soethbyshipping.service.QuoteService;
import com.crosby.soethbyshipping.service.ShipmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link QuoteResource} REST controller.
 */
@SpringBootTest(classes = SoethbyShippingApp.class)
@ExtendWith({ RedisTestContainerExtension.class, MockitoExtension.class })
@AutoConfigureMockMvc
@WithMockUser
class QuoteResourceIT {

    private static final Float DEFAULT_HEIGHT = 1F;
    private static final Float UPDATED_HEIGHT = 2F;

    private static final Float DEFAULT_LENGTH = 1F;
    private static final Float UPDATED_LENGTH = 2F;

    private static final Float DEFAULT_WIDTH = 1F;
    private static final Float UPDATED_WIDTH = 2F;

    private static final Float DEFAULT_WEIGHT = 1F;
    private static final Float UPDATED_WEIGHT = 2F;

    private static final Float DEFAULT_QUOTE = 1F;
    private static final Float UPDATED_QUOTE = 2F;

    private static final Integer DEFAULT_DURATION_IN_DAYS = 1;
    private static final Integer UPDATED_DURATION_IN_DAYS = 2;

    private static final String DEFAULT_PROVIDER = "UPS";
    private static final String UPDATED_PROVIDER = "FEDEX";

    private static final boolean DEFAULT_PERSIST = false;
    private static final boolean UPDATED_PERSIST = true;

    private static final String DEFAULT_CITY = "AAAAA";
    private static final String UPDATED_CITY = "BBBBB";

    private static final Integer DEFAULT_ZIP = 11111;
    private static final Integer UPDATED_ZIP = 22222;

    private static final String DEFAULT_STREET_ADDRESS = "AAAAA";
    private static final String UPDATED_STREET_ADDRESS = "BBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBB";

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private ShipmentService shipmentService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restShipmentMockMvc;

    @Autowired
    private QuoteRepository quoteRepository;

    @Autowired
    private QuoteService quoteService;

    @Autowired
    private MockMvc restQuoteMockMvc;

    private Shipment shipment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Shipment createEntity(EntityManager em) {
        Shipment shipment = new Shipment()
            .height(DEFAULT_HEIGHT)
            .length(DEFAULT_LENGTH)
            .width(DEFAULT_WIDTH)
            .weight(DEFAULT_WEIGHT);
        Shipment shipmentWithQuote = shipment.addQuote(createQuote(shipment));
        shipment.setDestination(createAddress());
        shipment.setSource(createAddress());
        return shipmentWithQuote;
    }

    public static Quote createQuote(Shipment shipment){
        Quote quote = new Quote()
            .shipment(shipment)
            .quote(DEFAULT_QUOTE)
            .duration(DEFAULT_DURATION_IN_DAYS)
            .provider(DEFAULT_PROVIDER)
            .persist(DEFAULT_PERSIST);
        return quote;
    }

    public static Address createAddress(){
        var address = new Address()
            .city(DEFAULT_CITY)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .streetName(DEFAULT_STREET_ADDRESS)
            .zip(DEFAULT_ZIP);

        return address;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Shipment createUpdatedEntity(EntityManager em) {
        Shipment shipment = new Shipment()
            .height(UPDATED_HEIGHT)
            .length(UPDATED_LENGTH)
            .width(UPDATED_WIDTH)
            .weight(UPDATED_WEIGHT);
        Shipment shipmentWithQuote = shipment.addQuote(createUpdatedQuote(shipment));
        return shipmentWithQuote;
    }

    public static Quote createUpdatedQuote(Shipment shipment){
        Quote quote = new Quote()
            .shipment(shipment)
            .quote(UPDATED_QUOTE)
            .duration(UPDATED_DURATION_IN_DAYS)
            .provider(UPDATED_PROVIDER)
            .persist(UPDATED_PERSIST);
        return quote;
    }

    public static Address createUpdatedAddress(){
        var address = new Address()
            .city(UPDATED_CITY)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .streetName(UPDATED_STREET_ADDRESS)
            .zip(UPDATED_ZIP);

        return address;
    }

    @BeforeEach
    void initTest() {
        shipment = createEntity(em);
    }

    @Test
    @Transactional
    void getQuote() throws Exception {
        // Initialize the database
        shipmentRepository.saveAndFlush(shipment);

        var quote = shipment.getQuotes().iterator().next();
        // Get the quote
        restQuoteMockMvc.perform(get("/api/quote/{id}", quote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(quote.getId().intValue()))
            .andExpect(jsonPath("$.provider").value(DEFAULT_PROVIDER))
            .andExpect(jsonPath("$.quote").value(DEFAULT_QUOTE.doubleValue()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION_IN_DAYS));

    }
    @Test
    @Transactional
    void getNonExistingQuote() throws Exception {
        // Get the quote
        restQuoteMockMvc.perform(get("/api/quote/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }


    @Test
    @Transactional
    void persistNonExistingQuote() throws Exception {
        int databaseSizeBeforeUpdate = quoteRepository.findAll().size();
        int outOfBoundsId = databaseSizeBeforeUpdate+1;

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuoteMockMvc.perform(put("/api/quote/persist/{id}", outOfBoundsId))
            .andExpect(status().isNotFound());

        // Validate the Quote in the database
        List<Quote> quoteList = quoteRepository.findAll();
        assertThat(quoteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQuote() throws Exception {
        // Initialize the database
        shipmentRepository.saveAndFlush(shipment);

        List<Shipment> shipment1 = shipmentRepository.findAll();
        var quote = shipment.getQuotes().iterator().next();

        int databaseSizeBeforeDelete = quoteRepository.findAll().size();

        // Delete the quote
        restQuoteMockMvc.perform(delete("/api/quote/{id}", quote.getId()))
        .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Quote> quoteList = quoteRepository.findAll();
        assertThat(quoteList).hasSize(databaseSizeBeforeDelete - 1);
    }

//    @Test
//    @Transactional
//    //requires https://github.com/gzurowski/mountebank-shipping
//    void getQuotes(EntityManager em) throws Exception {
//        int databaseSizeBefore = quoteRepository.findAll().size();
//        ObjectMapper mapper = new ObjectMapper();
//
//        restQuoteMockMvc.perform(post("/quotes/requestQuotes")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(mapper.writeValueAsString(shipment)))
//            .andExpect(status().isOk());
//
//        assertThat(quoteRepository.findAll().size()).isGreaterThan(databaseSizeBefore);
//
//    }
}
