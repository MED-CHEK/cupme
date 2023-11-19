package com.cupme.service.mapper;

import com.cupme.domain.Protocol;
import com.cupme.service.dto.ProtocolDTO;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * Mapper for the entity {@link Protocol} and its DTO called {@link Protocol}.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class ProtocolMapper {

    public List<ProtocolDTO> protocolsToProtocolDTOs(List<Protocol> protocols) {
        return protocols.stream().filter(Objects::nonNull).map(this::protocolToProtocolDTO).collect(Collectors.toList());
    }

    public ProtocolDTO protocolToProtocolDTO(Protocol protocol) {
        return new ProtocolDTO(protocol);
    }

    public List<Protocol> protocolDTOsToProtocols(List<ProtocolDTO> protocolDTOS) {
        return protocolDTOS.stream().filter(Objects::nonNull).map(this::protocolDTOToProtocol).collect(Collectors.toList());
    }

    public Protocol protocolDTOToProtocol(ProtocolDTO protocolDTO) {
        if (protocolDTO == null) {
            return null;
        } else {
            Protocol protocol = new Protocol();
            protocol.setId(protocolDTO.getId());
            protocol.setName(protocolDTO.getName());
            protocol.setType(protocolDTO.getType());
            protocol.setShortDescription(protocolDTO.getShortDescription());
            protocol.setDescription(protocolDTO.getDescription());
            protocol.setPrice(protocolDTO.getPrice());
            protocol.setTags(protocolDTO.getTags());
            protocol.setCategories(protocolDTO.getCategories());
            protocol.setProducts(protocolDTO.getProducts());

            return protocol;
        }
    }
}
