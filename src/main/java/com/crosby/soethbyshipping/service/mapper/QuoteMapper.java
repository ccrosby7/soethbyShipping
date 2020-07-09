package com.crosby.soethbyshipping.service.mapper;


import com.crosby.soethbyshipping.domain.*;
import com.crosby.soethbyshipping.service.dto.QuoteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Quote} and its DTO {@link QuoteDTO}.
 */
@Mapper(componentModel = "spring", uses = {ShipmentMapper.class})
public interface QuoteMapper extends EntityMapper<QuoteDTO, Quote> {

    @Mapping(source = "shipment.id", target = "shipmentId")
    QuoteDTO toDto(Quote quote);

    @Mapping(source = "shipmentId", target = "shipment")
    Quote toEntity(QuoteDTO quoteDTO);

    default Quote fromId(Long id) {
        if (id == null) {
            return null;
        }
        Quote quote = new Quote();
        quote.setId(id);
        return quote;
    }
}
