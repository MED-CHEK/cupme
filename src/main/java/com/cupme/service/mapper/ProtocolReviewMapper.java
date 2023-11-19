package com.cupme.service.mapper;

import com.cupme.domain.ProtocolReview;
import com.cupme.service.dto.ProtocolReviewDTO;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * Mapper for the entity {@link ProtocolReview} and its DTO called {@link ProtocolReview}.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class ProtocolReviewMapper {

    public List<ProtocolReviewDTO> protocolReviewsToProtocolReviewDTOs(List<ProtocolReview> protocolReviews) {
        return protocolReviews.stream().filter(Objects::nonNull).map(this::protocolReviewToProtocolReviewDTO).collect(Collectors.toList());
    }

    public ProtocolReviewDTO protocolReviewToProtocolReviewDTO(ProtocolReview protocolReview) {
        return new ProtocolReviewDTO(protocolReview);
    }

    public List<ProtocolReview> protocolReviewDTOsToProtocolReviews(List<ProtocolReviewDTO> protocolReviewDTOS) {
        return protocolReviewDTOS
            .stream()
            .filter(Objects::nonNull)
            .map(this::protocolReviewDTOToProtocolReview)
            .collect(Collectors.toList());
    }

    public ProtocolReview protocolReviewDTOToProtocolReview(ProtocolReviewDTO protocolReviewDTO) {
        if (protocolReviewDTO == null) {
            return null;
        } else {
            ProtocolReview protocolReview = new ProtocolReview();
            protocolReview.setId(protocolReviewDTO.getId());
            protocolReview.setRating(protocolReviewDTO.getRating());
            protocolReview.setComment(protocolReviewDTO.getComment());
            protocolReview.setProtocol(protocolReviewDTO.getProtocol());
            protocolReview.setUser(protocolReviewDTO.getUser());

            return protocolReview;
        }
    }
}
