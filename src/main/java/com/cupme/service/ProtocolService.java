package com.cupme.service;

import com.cupme.repository.ProtocolRepository;
import com.cupme.service.dto.MyProtocolDetailDTO;
import com.cupme.service.dto.ProtocolCartDTO;
import com.cupme.service.dto.ProtocolDTO;
import com.cupme.service.dto.ProtocolDetailDTO;
import com.cupme.service.mapper.ProtocolMapper;
import com.cupme.service.utils.AssetFilesService;
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

    private final AssetFilesService assetFilesService;

    public ProtocolService(
        ProtocolRepository protocolRepository,
        ProtocolMapper protocolMapper,
        CacheManager cacheManager,
        AssetFilesService assetFilesService
    ) {
        this.protocolRepository = protocolRepository;
        this.protocolMapper = protocolMapper;
        this.cacheManager = cacheManager;
        this.assetFilesService = assetFilesService;
    }

    public List<ProtocolDTO> getProtocols() {
        List<ProtocolDTO> protocols = protocolMapper.protocolsToProtocolDTOs(protocolRepository.findAll());

        protocols.forEach(protocolDTO -> {
            protocolDTO
                .getPictures()
                .forEach(pictureDTO -> {
                    pictureDTO.setFile(assetFilesService.getFile(pictureDTO.getFile()));
                    pictureDTO.setProtocol(null);
                });
        });
        return protocols;
    }

    public List<ProtocolCartDTO> getProtocolCards() {
        List<ProtocolCartDTO> protocols = protocolMapper.protocolsToProtocolCartDTOs(protocolRepository.findAll());

        protocols.forEach(protocolDTO -> {
            protocolDTO.getPicture().setFile(assetFilesService.getFile(protocolDTO.getPicture().getFile()));
            protocolDTO.getPicture().setProtocol(null);
        });

        return protocols;
    }

    public ProtocolDTO getProtocol(long id) {
        ProtocolDTO protocolDTO = protocolMapper.protocolToProtocolDTO(protocolRepository.findById(id).get());
        protocolDTO
            .getPictures()
            .forEach(pictureDTO -> {
                pictureDTO.setFile(assetFilesService.getFile(pictureDTO.getFile()));
                pictureDTO.setProtocol(null);
            });
        protocolDTO
            .getProductDTOs()
            .forEach(productDTO -> {
                productDTO
                    .getPictures()
                    .forEach(pictureDTO -> {
                        pictureDTO.setFile(assetFilesService.getFile(pictureDTO.getFile()));
                        pictureDTO.setProduct(null);
                    });
            });
        return protocolDTO;
    }

    public ProtocolDetailDTO getProtocolDetail(long id) {
        ProtocolDetailDTO protocolDetailDTO = protocolMapper.protocolToProtocolDetailDTO(protocolRepository.findById(id).get());
        protocolDetailDTO.getPicture().setFile(assetFilesService.getFile(protocolDetailDTO.getPicture().getFile()));
        protocolDetailDTO.getPicture().setProtocol(null);

        protocolDetailDTO
            .getProductDTOs()
            .forEach(productDTO -> {
                productDTO.getPicture().setFile(assetFilesService.getFile(productDTO.getPicture().getFile()));
                productDTO.getPicture().setProduct(null);
            });

        return protocolDetailDTO;
    }

    public MyProtocolDetailDTO getMyProtocol(long id) {
        MyProtocolDetailDTO myProtocolDetailDTO = protocolMapper.protocolToMyProtocolDetailDTO(protocolRepository.findById(id).get());

        return myProtocolDetailDTO;
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
