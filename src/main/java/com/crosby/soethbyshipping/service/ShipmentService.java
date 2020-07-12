package com.crosby.soethbyshipping.service;

import com.crosby.soethbyshipping.domain.Shipment;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.crosby.soethbyshipping.domain.Shipment}.
 */
public interface ShipmentService {

    /**
     * Save a shipment.
     *
     * @param shipment the entity to save.
     * @return the persisted entity.
     */
    Shipment save(Shipment shipment);

    /**
     * Get all the shipments.
     *
     * @return the list of entities.
     */
    List<Shipment> findAll();


    /**
     * Get the "id" shipment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Shipment> findOne(Long id);

    /**
     * Delete the "id" shipment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

}
