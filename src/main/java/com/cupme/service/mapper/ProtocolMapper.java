package com.cupme.service.mapper;

import com.cupme.domain.Protocol;
import com.cupme.service.dto.MyProtocolDetailDTO;
import com.cupme.service.dto.ProtocolCartDTO;
import com.cupme.service.dto.ProtocolDTO;
import com.cupme.service.dto.ProtocolDetailDTO;
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

    private final TagMapper tagMapper;

    private final CategoryMapper categoryMapper;

    private final ProductMapper productMapper;

    public ProtocolMapper(TagMapper tagMapper, CategoryMapper categoryMapper, ProductMapper productMapper) {
        this.tagMapper = tagMapper;
        this.categoryMapper = categoryMapper;
        this.productMapper = productMapper;
    }

    public List<ProtocolDTO> protocolsToProtocolDTOs(List<Protocol> protocols) {
        return protocols.stream().filter(Objects::nonNull).map(this::protocolToProtocolDTO).collect(Collectors.toList());
    }

    public ProtocolDTO protocolToProtocolDTO(Protocol protocol) {
        return new ProtocolDTO(protocol);
    }

    public List<ProtocolDetailDTO> protocolsToProtocolDetailDTOs(List<Protocol> protocols) {
        return protocols.stream().filter(Objects::nonNull).map(this::protocolToProtocolDetailDTO).collect(Collectors.toList());
    }

    public ProtocolDetailDTO protocolToProtocolDetailDTO(Protocol protocol) {
        return new ProtocolDetailDTO(protocol);
    }

    public List<ProtocolCartDTO> protocolsToProtocolCartDTOs(List<Protocol> protocols) {
        return protocols.stream().filter(Objects::nonNull).map(this::protocolToProtocolCartDTO).collect(Collectors.toList());
    }

    public ProtocolCartDTO protocolToProtocolCartDTO(Protocol protocol) {
        return new ProtocolCartDTO(protocol);
    }

    public MyProtocolDetailDTO protocolToMyProtocolDetailDTO(Protocol protocol) {
        return new MyProtocolDetailDTO(protocol);
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
            protocol.setPoseTime(protocolDTO.getPoseTime());
            protocol.setTags(tagMapper.tagDTOsToTags(protocolDTO.getTagDTOs()));
            protocol.setCategories(categoryMapper.categoryDTOsToCategories(protocolDTO.getCategoryDTOs()));
            protocol.setProducts(productMapper.productDTOsToProducts(protocolDTO.getProductDTOs()));

            return protocol;
        }
    }

    public Protocol protocolCartDTOToProtocol(ProtocolCartDTO protocolCartDTO) {
        if (protocolCartDTO == null) {
            return null;
        } else {
            Protocol protocol = new Protocol();
            protocol.setId(protocolCartDTO.getId());
            protocol.setName(protocolCartDTO.getName());
            protocol.setPrice(protocolCartDTO.getPrice());

            return protocol;
        }
    }
}
