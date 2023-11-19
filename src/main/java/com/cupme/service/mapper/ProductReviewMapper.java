package com.cupme.service.mapper;

import com.cupme.domain.ProductReview;
import com.cupme.service.dto.ProductReviewDTO;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * Mapper for the entity {@link ProductReview} and its DTO called {@link ProductReview}.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class ProductReviewMapper {

    public List<ProductReviewDTO> productReviewsToProductReviewDTOs(List<ProductReview> productReviews) {
        return productReviews.stream().filter(Objects::nonNull).map(this::productReviewToProductReviewDTO).collect(Collectors.toList());
    }

    public ProductReviewDTO productReviewToProductReviewDTO(ProductReview productReview) {
        return new ProductReviewDTO(productReview);
    }

    public List<ProductReview> productReviewDTOsToProductReviews(List<ProductReviewDTO> productReviewDTOS) {
        return productReviewDTOS.stream().filter(Objects::nonNull).map(this::productReviewDTOToProductReview).collect(Collectors.toList());
    }

    public ProductReview productReviewDTOToProductReview(ProductReviewDTO productReviewDTO) {
        if (productReviewDTO == null) {
            return null;
        } else {
            ProductReview productReview = new ProductReview();
            productReview.setId(productReviewDTO.getId());
            productReview.setRating(productReviewDTO.getRating());
            productReview.setComment(productReviewDTO.getComment());
            productReview.setProduct(productReviewDTO.getProduct());
            productReview.setUser(productReviewDTO.getUser());

            return productReview;
        }
    }
}
