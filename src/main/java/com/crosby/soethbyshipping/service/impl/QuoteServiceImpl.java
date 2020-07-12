package com.crosby.soethbyshipping.service.impl;

import com.crosby.soethbyshipping.service.QuoteService;
import com.crosby.soethbyshipping.domain.Quote;
import com.crosby.soethbyshipping.repository.QuoteRepository;
import com.crosby.soethbyshipping.service.dto.QuoteDTO;
import com.crosby.soethbyshipping.service.mapper.QuoteMapper;
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

    private final QuoteMapper quoteMapper;

    public QuoteServiceImpl(QuoteRepository quoteRepository, QuoteMapper quoteMapper) {
        this.quoteRepository = quoteRepository;
        this.quoteMapper = quoteMapper;
    }

    @Override
    public QuoteDTO save(QuoteDTO quoteDTO) {
        log.debug("Request to save Quote : {}", quoteDTO);
        Quote quote = quoteMapper.toEntity(quoteDTO);
        quote = quoteRepository.save(quote);
        return quoteMapper.toDto(quote);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuoteDTO> findAll() {
        log.debug("Request to get all Quotes");
        return quoteRepository.findAll().stream()
            .map(quoteMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<QuoteDTO> findOne(Long id) {
        log.debug("Request to get Quote : {}", id);
        return quoteRepository.findById(id)
            .map(quoteMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Quote : {}", id);
        quoteRepository.deleteById(id);
    }

    @Override
    public boolean persist(Long id) {
        var optionalQuote = quoteRepository.findById(id);
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
        var optionalQuote = quoteRepository.findById(id);
        if(optionalQuote.isPresent()){

        }
    }

}
