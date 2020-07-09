package com.crosby.soethbyshipping.service.mapper;


import com.crosby.soethbyshipping.domain.*;
import com.crosby.soethbyshipping.service.dto.ShipmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Shipment} and its DTO {@link ShipmentDTO}.
 */
@Mapper(componentModel = "spring", uses = {AddressMapper.class})
public interface ShipmentMapper extends EntityMapper<ShipmentDTO, Shipment> {

    @Mapping(source = "destination.id", target = "destinationId")
    @Mapping(source = "source.id", target = "sourceId")
    ShipmentDTO toDto(Shipment shipment);

    @Mapping(source = "destinationId", target = "destination")
    @Mapping(source = "sourceId", target = "source")
    @Mapping(target = "quotes", ignore = true)
    @Mapping(target = "removeQuote", ignore = true)
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
