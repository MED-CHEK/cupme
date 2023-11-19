package com.cupme.service;

import com.cupme.repository.ProtocolRepository;
import com.cupme.service.dto.ProtocolDTO;
import com.cupme.service.mapper.ProtocolMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing protocols.
 */
@Service
@Transactional
public class ProtocolService {

    private final Logger log = LoggerFactory.getLogger(ProtocolService.class);

    private final ProtocolRepository protocolRepository;

    private final ProtocolMapper protocolMapper;
    private final CacheManager cacheManager;

    public ProtocolService(ProtocolRepository protocolRepository, ProtocolMapper protocolMapper, CacheManager cacheManager) {
        this.protocolRepository = protocolRepository;
        this.protocolMapper = protocolMapper;
        this.cacheManager = cacheManager;
    }

    public List<ProtocolDTO> getProtocols() {
        return protocolMapper.protocolsToProtocolDTOs(protocolRepository.findAll());
    }

    public ProtocolDTO getProtocol(long id) {
        return protocolMapper.protocolToProtocolDTO(protocolRepository.findById(id).get());
    }

    public ProtocolDTO createProtocol(ProtocolDTO protocolDTO) {
        return protocolMapper.protocolToProtocolDTO(protocolRepository.save(protocolMapper.protocolDTOToProtocol(protocolDTO)));
    }

    public ProtocolDTO updateProtocol(ProtocolDTO protocolDTO) {
        return protocolMapper.protocolToProtocolDTO(protocolRepository.save(protocolMapper.protocolDTOToProtocol(protocolDTO)));
    }

    public void deleteProtocol(long id) {
        protocolRepository.deleteById(id);
    }
}
