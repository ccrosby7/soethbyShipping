package com.crosby.soethbyshipping.service.impl;

import com.crosby.soethbyshipping.domain.Quote;
import com.crosby.soethbyshipping.service.AddressService;
import com.crosby.soethbyshipping.service.QuoteService;
import com.crosby.soethbyshipping.service.ShipmentService;
import com.crosby.soethbyshipping.domain.Shipment;
import com.crosby.soethbyshipping.repository.ShipmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Shipment}.
 */
@Service
@Transactional
public class ShipmentServiceImpl implements ShipmentService {

    private final Logger log = LoggerFactory.getLogger(ShipmentServiceImpl.class);

    private final ShipmentRepository shipmentRepository;
    private final AddressService addressService;

    private QuoteService quoteService;

    public ShipmentServiceImpl(ShipmentRepository shipmentRepository,
                               AddressService addressService) {
        this.shipmentRepository = shipmentRepository;
        this.addressService = addressService;
    }

    @Autowired
    public void setQuoteService(QuoteService quoteService){
        this.quoteService = quoteService; //breaks circular dependency
    }

    @Override
    public Shipment save(Shipment shipment) {
        log.debug("Request to save Shipment : {}", shipment);
        return shipmentRepository.save(shipment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Shipment> findAll() {
        log.debug("Request to get all Shipments");
        return shipmentRepository.findAll().stream().collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Shipment> findOne(Long id) {
        log.debug("Request to get Shipment : {}", id);
        return shipmentRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Shipment : {}", id);
        shipmentRepository.deleteById(id);
    }

}
