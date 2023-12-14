package com.cupme.service.mapper;

import com.cupme.domain.Picture;
import com.cupme.service.dto.PictureDTO;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * Mapper for the entity {@link Picture} and its DTO called {@link Picture}.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class PictureMapper {

    public List<PictureDTO> pictureToPictureDtos(List<Picture> pictures) {
        return pictures.stream().filter(Objects::nonNull).map(this::pictureToPictureDto).collect(Collectors.toList());
    }

    public PictureDTO pictureToPictureDto(Picture picture) {
        return new PictureDTO(picture);
    }

    public List<Picture> pictureDtoToPictures(List<PictureDTO> pictureDTOS) {
        return pictureDTOS.stream().filter(Objects::nonNull).map(this::pictureDtoToPicture).collect(Collectors.toList());
    }

    public Picture pictureDtoToPicture(PictureDTO pictureDTO) {
        if (pictureDTO == null) {
            return null;
        } else {
            Picture picture = new Picture();
            picture.setId(pictureDTO.getId());
            picture.setName(pictureDTO.getName());
            picture.setFile(pictureDTO.getFile());
            picture.setMain(pictureDTO.getMain());
            picture.setProtocol(pictureDTO.getProtocol());
            picture.setProduct(pictureDTO.getProduct());

            return picture;
        }
    }
}
