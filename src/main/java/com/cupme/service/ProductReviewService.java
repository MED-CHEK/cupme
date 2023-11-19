package com.cupme.service;

import com.cupme.repository.ProductReviewRepository;
import com.cupme.service.dto.ProductReviewDTO;
import com.cupme.service.mapper.ProductReviewMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing product reviews.
 */
@Service
@Transactional
public class ProductReviewService {

    private final Logger log = LoggerFactory.getLogger(ProductReviewService.class);

    private final ProductReviewRepository productReviewRepository;

    private final ProductReviewMapper productReviewMapper;
    private final CacheManager cacheManager;

    public ProductReviewService(
        ProductReviewRepository productReviewRepository,
        ProductReviewMapper productReviewMapper,
        CacheManager cacheManager
    ) {
        this.productReviewRepository = productReviewRepository;
        this.productReviewMapper = productReviewMapper;
        this.cacheManager = cacheManager;
    }

    public List<ProductReviewDTO> getProductReviews() {
        return productReviewMapper.productReviewsToProductReviewDTOs(productReviewRepository.findAll());
    }

    public ProductReviewDTO getProductReview(long id) {
        return productReviewMapper.productReviewToProductReviewDTO(productReviewRepository.findById(id).get());
    }

    public ProductReviewDTO createProductReview(ProductReviewDTO productReviewDTO) {
        return productReviewMapper.productReviewToProductReviewDTO(
            productReviewRepository.save(productReviewMapper.productReviewDTOToProductReview(productReviewDTO))
        );
    }

    public ProductReviewDTO updateProductReview(ProductReviewDTO productReviewDTO) {
        return productReviewMapper.productReviewToProductReviewDTO(
            productReviewRepository.save(productReviewMapper.productReviewDTOToProductReview(productReviewDTO))
        );
    }

    public void deleteProductReview(long id) {
        productReviewRepository.deleteById(id);
    }
}
