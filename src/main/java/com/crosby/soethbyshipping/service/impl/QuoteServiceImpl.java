package com.crosby.soethbyshipping.service.impl;

import com.crosby.soethbyshipping.config.ShippingConfiguration;
import com.crosby.soethbyshipping.domain.Shipment;
import com.crosby.soethbyshipping.enums.ResponseFormat;
import com.crosby.soethbyshipping.service.QuoteService;
import com.crosby.soethbyshipping.domain.Quote;
import com.crosby.soethbyshipping.repository.QuoteRepository;
import com.crosby.soethbyshipping.service.ShipmentService;
import com.crosby.soethbyshipping.util.QuoteRequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Quote}.
 */
@Service
@Transactional
public class QuoteServiceImpl implements QuoteService {

    private final Logger log = LoggerFactory.getLogger(QuoteServiceImpl.class);

    private final QuoteRepository quoteRepository;
    private final ShipmentService shipmentService;

    private final ShippingConfiguration shippingConfiguration;
    private final QuoteRequestUtil quoteRequestUtil;

    public QuoteServiceImpl(QuoteRepository quoteRepository, ShipmentService shipmentService,
                            ShippingConfiguration shippingConfiguration, QuoteRequestUtil quoteRequestUtil) {
        this.quoteRepository = quoteRepository;
        this.shipmentService = shipmentService;
        this.shippingConfiguration = shippingConfiguration;
        this.quoteRequestUtil = quoteRequestUtil;
    }

    @Override
    public Quote save(Quote quote) {
        log.debug("Request to save Quote : {}", quote);
        return quoteRepository.save(quote);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Quote> findAll() {
        log.debug("Request to get all Quotes");
        return quoteRepository.findAll().stream().collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Quote> findOne(Long id) {
        log.debug("Request to get Quote : {}", id);
        return quoteRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Quote : {}", id);
        quoteRepository.deleteById(id);
    }

    @Override
    public boolean persist(Long id) {
        var optionalQuote = findOne(id);
        if(optionalQuote.isPresent()){
            var quoteToPersist = optionalQuote.get();
            var trashQuotes = quoteToPersist.getShipment().getQuotes();
            trashQuotes.remove(quoteToPersist);
            trashQuotes.forEach(quote -> delete(quote.getId())); //trash unused quotes so the db doesn't get cluttered
            quoteToPersist.setPersist(true); //flippable?
            quoteRepository.save(quoteToPersist);
            return true;
        }
        return false;
    }

    @Override
    public void deleteChain(Long id) {
        var optionalQuote = findOne(id);
        if(optionalQuote.isPresent()){
            var quoteToDelete = optionalQuote.get();
            var shipmentToDelete = quoteToDelete.getShipment();
            shipmentService.delete(shipmentToDelete.getId()); //will purge all data associated to a booked quote
        }
    }

    @Override
    public List<Quote> getQuotesFromProviders(Shipment shipment){
        var jsonUrls = shippingConfiguration.getJson();
        var xmlUrls = shippingConfiguration.getXML();

        var quotes = quoteRequestUtil.requestQuotes(shipment, jsonUrls, ResponseFormat.JSON);
        quotes.addAll(quoteRequestUtil.requestQuotes(shipment, xmlUrls, ResponseFormat.XML));
        return quotes;
    }

}
