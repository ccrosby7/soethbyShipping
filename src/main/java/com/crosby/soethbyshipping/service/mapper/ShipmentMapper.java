package com.crosby.soethbyshipping.service.mapper;


import com.crosby.soethbyshipping.domain.*;
import com.crosby.soethbyshipping.service.dto.ShipmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Shipment} and its DTO {@link ShipmentDTO}.
 */
@Mapper(componentModel = "spring", uses = {QuoteMapper.class})
public interface ShipmentMapper extends EntityMapper<ShipmentDTO, Shipment> {

    @Mapping(source = "quote.id", target = "quoteId")
    ShipmentDTO toDto(Shipment shipment);

    @Mapping(source = "quoteId", target = "quote")
    Shipment toEntity(ShipmentDTO shipmentDTO);

    default Shipment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Shipment shipment = new Shipment();
        shipment.setId(id);
        return shipment;
    }
}
