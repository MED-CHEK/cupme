package com.cupme.service;

import com.cupme.domain.Picture;
import com.cupme.domain.Product;
import com.cupme.domain.Protocol;
import com.cupme.domain.enumeration.ProtocolType;
import com.cupme.repository.PictureRepository;
import com.cupme.repository.ProductRepository;
import com.cupme.repository.ProtocolRepository;
import com.cupme.service.dto.*;
import com.cupme.service.mapper.PictureMapper;
import com.cupme.service.mapper.ProtocolMapper;
import com.cupme.service.utils.AssetFilesService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final ProductRepository productRepository;
    private final PictureRepository pictureRepository;
    private final ProtocolMapper protocolMapper;
    private final PictureMapper pictureMapper;

    private final AssetFilesService assetFilesService;

    public ProtocolService(
        ProtocolRepository protocolRepository,
        ProductRepository productRepository,
        PictureRepository pictureRepository,
        ProtocolMapper protocolMapper,
        PictureMapper pictureMapper,
        AssetFilesService assetFilesService
    ) {
        this.protocolRepository = protocolRepository;
        this.productRepository = productRepository;
        this.pictureRepository = pictureRepository;
        this.protocolMapper = protocolMapper;
        this.pictureMapper = pictureMapper;
        this.assetFilesService = assetFilesService;
    }

    public List<ProtocolDTO> getProtocols() {
        List<ProtocolDTO> protocols = protocolMapper.protocolsToProtocolDTOs(protocolRepository.findAll());

        protocols.forEach(protocolDTO -> {
            protocolDTO
                .getPictures()
                .forEach(pictureDTO -> {
                    pictureDTO.setFile(assetFilesService.getFile(pictureDTO.getFile()));
                });
        });
        return protocols;
    }

    public List<ProtocolCartDTO> getProtocolCards() {
        List<ProtocolCartDTO> protocols = protocolMapper.protocolsToProtocolCartDTOs(protocolRepository.findAll());

        protocols.forEach(protocolDTO -> {
            protocolDTO.getPicture().setFile(assetFilesService.getFile(protocolDTO.getPicture().getFile()));
        });

        return protocols;
    }

    public List<ProtocolCartDTO> getGenericProtocol() {
        List<ProtocolCartDTO> protocols = protocolMapper.protocolsToProtocolCartDTOs(
            protocolRepository.findProtocolByType(ProtocolType.GENERIC)
        );

        protocols.forEach(protocolDTO -> {
            protocolDTO.getPicture().setFile(assetFilesService.getFile(protocolDTO.getPicture().getFile()));
        });

        return protocols;
    }

    public ProtocolDTO getProtocol(long id) {
        ProtocolDTO protocolDTO = protocolMapper.protocolToProtocolDTO(protocolRepository.findById(id).get());
        protocolDTO
            .getPictures()
            .forEach(pictureDTO -> {
                pictureDTO.setFile(assetFilesService.getFile(pictureDTO.getFile()));
            });
        protocolDTO
            .getProductDTOs()
            .forEach(productDTO -> {
                productDTO
                    .getPictures()
                    .forEach(pictureDTO -> {
                        pictureDTO.setFile(assetFilesService.getFile(pictureDTO.getFile()));
                    });
            });
        return protocolDTO;
    }

    public ProtocolDetailDTO getProtocolDetail(long id) {
        ProtocolDetailDTO protocolDetailDTO = protocolMapper.protocolToProtocolDetailDTO(protocolRepository.findById(id).get());
        protocolDetailDTO
            .getPictures()
            .forEach(pictureDTO -> {
                pictureDTO.setFile(assetFilesService.getFile(pictureDTO.getFile()));
            });

        protocolDetailDTO
            .getProductDTOs()
            .forEach(productDTO -> {
                productDTO.getPicture().setFile(assetFilesService.getFile(productDTO.getPicture().getFile()));
            });

        return protocolDetailDTO;
    }

    public MyProtocolDetailDTO getMyProtocol(long id) {
        MyProtocolDetailDTO myProtocolDetailDTO = protocolMapper.protocolToMyProtocolDetailDTO(protocolRepository.findById(id).get());

        return myProtocolDetailDTO;
    }

    public ProtocolDTO createProtocol(ProtocolDTO protocolDTO) {
        Protocol protocol = protocolMapper.protocolDTOToProtocol(protocolDTO);
        ProtocolDTO dto = protocolMapper.protocolToProtocolDTO(protocolRepository.save(protocol));

        assetFilesService.addFolder(dto.getId() + "");

        if (protocolDTO.getPictures() != null && protocolDTO.getPictures().size() > 0) {
            protocolDTO
                .getPictures()
                .forEach(picture -> {
                    if (picture.getMain()) {
                        picture.setName("main");
                    }
                    picture.setFile(assetFilesService.savePicture(picture, dto.getId()));
                    Picture toPicture = pictureMapper.pictureDtoToPicture(picture);
                    toPicture.setProtocol(protocol);
                    toPicture.setFile("content/images/" + dto.getId() + "/" + picture.getName());
                    pictureRepository.save(toPicture);
                });
        }
        return dto;
    }

    public ProtocolDTO updateProtocol(ProtocolDTO protocolDTO) {
        Protocol protocol = protocolMapper.protocolDTOToProtocol(protocolDTO);
        ProtocolDTO dto = protocolMapper.protocolToProtocolDTO(protocolRepository.save(protocol));

        pictureRepository.deleteAllByProtocolId(dto.getId());

        if (protocolDTO.getPictures() != null && protocolDTO.getPictures().size() > 0) {
            protocolDTO
                .getPictures()
                .forEach(picture -> {
                    if (picture.getMain()) {
                        picture.setName("main");
                    }
                    picture.setFile(assetFilesService.savePicture(picture, protocolDTO.getId()));
                    Picture toPicture = pictureMapper.pictureDtoToPicture(picture);
                    toPicture.setProtocol(protocol);
                    toPicture.setFile("content/images/" + protocolDTO.getId() + "/" + picture.getName());
                    pictureRepository.save(toPicture);
                });
        }
        return dto;
    }

    public void deleteProtocol(long id) {
        pictureRepository.deleteByProtocolId(id);
        Optional<Protocol> protocolOptional = protocolRepository.findById(id);
        if (protocolOptional.isPresent()) {
            Protocol protocol = protocolOptional.get();
            for (Product product : protocol.getProducts()) {
                product.getProtocols().remove(protocol);
                productRepository.save(product);
            }
            protocolRepository.delete(protocol);
        }
    }
}
