package com.crosby.soethbyshipping.service.impl;

import com.crosby.soethbyshipping.domain.Quote;
import com.crosby.soethbyshipping.repository.QuoteRepository;
import com.crosby.soethbyshipping.service.AddressService;
import com.crosby.soethbyshipping.service.QuoteService;
import com.crosby.soethbyshipping.service.ShipmentService;
import com.crosby.soethbyshipping.domain.Shipment;
import com.crosby.soethbyshipping.repository.ShipmentRepository;
import com.crosby.soethbyshipping.service.dto.ShipmentDTO;
import com.crosby.soethbyshipping.service.mapper.ShipmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private final ShipmentMapper shipmentMapper;
    private final QuoteService quoteService;
    private final AddressService addressService;

    public ShipmentServiceImpl(ShipmentRepository shipmentRepository, ShipmentMapper shipmentMapper,
                               QuoteService quoteService, AddressService addressService) {
        this.shipmentRepository = shipmentRepository;
        this.shipmentMapper = shipmentMapper;
        this.quoteService = quoteService;
        this.addressService = addressService;
    }

    @Override
    public ShipmentDTO save(ShipmentDTO shipmentDTO) {
        log.debug("Request to save Shipment : {}", shipmentDTO);
        Shipment shipment = shipmentMapper.toEntity(shipmentDTO);
        shipment = shipmentRepository.save(shipment);
        return shipmentMapper.toDto(shipment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShipmentDTO> findAll() {
        log.debug("Request to get all Shipments");
        return shipmentRepository.findAll().stream()
            .map(shipmentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ShipmentDTO> findOne(Long id) {
        log.debug("Request to get Shipment : {}", id);
        return shipmentRepository.findById(id)
            .map(shipmentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Shipment : {}", id);
        Optional<Shipment> shipment = shipmentRepository.findById(id);
        if(shipment.isPresent()){
            Shipment shipmentToDelete = shipment.get();
            Set<Quote> quotes = shipmentToDelete.getQuotes();
            quotes.forEach(quote -> quoteService.delete(quote.getId()) ); //clean out all associated to a shipment
            addressService.delete(shipmentToDelete.getDestination().getId());
            addressService.delete(shipmentToDelete.getSource().getId());
        }
        shipmentRepository.deleteById(id);
    }

}
