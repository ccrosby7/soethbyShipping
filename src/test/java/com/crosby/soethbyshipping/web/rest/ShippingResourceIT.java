package com.crosby.soethbyshipping.web.rest;

import com.crosby.soethbyshipping.RedisTestContainerExtension;
import com.crosby.soethbyshipping.SoethbyShippingApp;
import com.crosby.soethbyshipping.domain.Quote;
import com.crosby.soethbyshipping.domain.Shipment;
import com.crosby.soethbyshipping.repository.ShipmentRepository;
import com.crosby.soethbyshipping.service.ShipmentService;
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
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the {@link ShippingResource} REST controller.
 */
@SpringBootTest(classes = SoethbyShippingApp.class)
@ExtendWith({MockitoExtension.class })
@AutoConfigureMockMvc
@WithMockUser
public class ShippingResourceIT {
    private static final Float DEFAULT_HEIGHT = 1F;
    private static final Float UPDATED_HEIGHT = 2F;

    private static final Float DEFAULT_LENGTH = 1F;
    private static final Float UPDATED_LENGTH = 2F;

    private static final Float DEFAULT_WIDTH = 1F;
    private static final Float UPDATED_WIDTH = 2F;

    private static final Float DEFAULT_WEIGHT = 1F;
    private static final Float UPDATED_WEIGHT = 2F;

    private static final Float DEFAULT_PRICE = 1F;
    private static final Float UPDATED_PRICE = 2F;

    private static final Integer DEFAULT_DURATION_IN_DAYS = 1;
    private static final Integer UPDATED_DURATION_IN_DAYS = 2;

    private static final String DEFAULT_PROVIDER = "UPS";
    private static final String UPDATED_PROVIDER = "FEDEX";

    private static final boolean DEFAULT_PERSIST = false;
    private static final boolean UPDATED_PERSIST = true;


    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private ShipmentService shipmentService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restShipmentMockMvc;

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
        Shipment shipmentWithQuote = shipment.addQuote(createQuote(em, shipment));
        return shipmentWithQuote;
    }

    public static Quote createQuote(EntityManager em, Shipment shipment){
        Quote quote = new Quote()
            .shipment(shipment)
            .quote(DEFAULT_PRICE)
            .duration(DEFAULT_DURATION_IN_DAYS)
            .provider(DEFAULT_PROVIDER)
            .persist(DEFAULT_PERSIST);
        return quote;
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
        Shipment shipmentWithQuote = shipment.addQuote(createQuote(em, shipment));
        return shipmentWithQuote;
    }

    public static Quote createUpdatedQuote(EntityManager em, Shipment shipment){
        Quote quote = new Quote()
            .shipment(shipment)
            .quote(UPDATED_PRICE)
            .duration(UPDATED_DURATION_IN_DAYS)
            .provider(UPDATED_PROVIDER)
            .persist(UPDATED_PERSIST);
        return quote;
    }

    @BeforeEach
    public void initTest() {
        shipment = createEntity(em);
    }

    @Test
    @Transactional
    public void createShipment() throws Exception {
        int databaseSizeBeforeCreate = shipmentRepository.findAll().size();
        restShipmentMockMvc.perform(post("/api/shipments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shipment)))
            .andExpect(status().isCreated());

        // Validate the Shipment in the database
        List<Shipment> shipmentList = shipmentRepository.findAll();
        assertThat(shipmentList).hasSize(databaseSizeBeforeCreate + 1);
        Shipment testShipment = shipmentList.get(shipmentList.size() - 1);
        assertThat(testShipment.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testShipment.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testShipment.getWidth()).isEqualTo(DEFAULT_WIDTH);
        assertThat(testShipment.getWeight()).isEqualTo(DEFAULT_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllShipments() throws Exception {
        // Initialize the database
        shipmentRepository.saveAndFlush(shipment);

        // Get all the shipmentList
        restShipmentMockMvc.perform(get("/api/shipments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipment.getId().intValue())))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH.doubleValue())))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH.doubleValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].quotes").value(hasSize(1)));
    }

    @Test
    @Transactional
    public void getShipment() throws Exception {
        // Initialize the database
        shipmentRepository.saveAndFlush(shipment);

        // Get the shipment
        restShipmentMockMvc.perform(get("/api/shipments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipment.getId().intValue())))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH.doubleValue())))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH.doubleValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].quotes").value(hasSize(1)));
    }
    @Test
    @Transactional
    public void persistQuote() throws Exception {
        shipmentRepository.saveAndFlush(shipment);

        restShipmentMockMvc.perform(post("/api/shipments/ +
    }

    @Test
    @Transactional
    public void updateShipment() throws Exception {
        // Initialize the database
        shipmentRepository.saveAndFlush(shipment);

        int databaseSizeBeforeUpdate = shipmentRepository.findAll().size();

        // Update the shipment
        Shipment updatedShipment = shipmentRepository.findById(shipment.getId()).get();
        // Disconnect from session so that the updates on updatedShipment are not directly saved in db
        em.detach(updatedShipment);
        updatedShipment
            .height(UPDATED_HEIGHT)
            .length(UPDATED_LENGTH)
            .width(UPDATED_WIDTH)
            .weight(UPDATED_WEIGHT);

        restShipmentMockMvc.perform(put("/api/shipments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedShipment)))
            .andExpect(status().isOk());

        // Validate the Shipment in the database
        List<Shipment> shipmentList = shipmentRepository.findAll();
        assertThat(shipmentList).hasSize(databaseSizeBeforeUpdate);
        Shipment testShipment = shipmentList.get(shipmentList.size() - 1);
        Quote testQuote = testShipment.getQuotes().iterator().next();
        assertThat(testShipment.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testShipment.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testShipment.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testShipment.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testQuote.getQuote()).isEqualTo(UPDATED_PRICE);
        assertThat(testQuote.getDuration()).isEqualTo(UPDATED_DURATION_IN_DAYS);
        assertThat(testQuote.getProvider()).isEqualTo(UPDATED_PROVIDER);
    }

    @Test
    @Transactional
    public void updateNonExistingShipment() throws Exception {
        int databaseSizeBeforeUpdate = shipmentRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShipmentMockMvc.perform(put("/api/shipments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shipment)))
            .andExpect(status().isBadRequest());

        // Validate the Shipment in the database
        List<Shipment> shipmentList = shipmentRepository.findAll();
        assertThat(shipmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteShipment() throws Exception {
        // Initialize the database
        shipmentRepository.saveAndFlush(shipment);

        int databaseSizeBeforeDelete = shipmentRepository.findAll().size();

        // Delete the shipment
        restShipmentMockMvc.perform(delete("/api/shipments/{id}", shipment.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Shipment> shipmentList = shipmentRepository.findAll();
        assertThat(shipmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

