package com.cupme.web.rest;

import com.cupme.security.AuthoritiesConstants;
import com.cupme.service.ProductReviewService;
import com.cupme.service.dto.ProductReviewDTO;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductReviewResource {

    private final Logger log = LoggerFactory.getLogger(ProductReviewResource.class);

    private final ProductReviewService productReviewService;

    public ProductReviewResource(ProductReviewService productReviewService) {
        this.productReviewService = productReviewService;
    }

    /**
     * {@code GET /productReviews} : get all productReviews with only the public informations - calling this are allowed for anyone.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all productReviews.
     */
    @GetMapping("/productReviews")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<List<ProductReviewDTO>> getAllProductReviews() {
        log.debug("REST request to get all public ProductReview names");

        final List<ProductReviewDTO> productReviewes = productReviewService.getProductReviews();
        return ResponseEntity.ok().body(productReviewes);
    }

    /**
     * {@code GET /productReviews/:id} : get the "id" productReview.
     * @param id the id of the productReviewDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productReviewDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/productReviews/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<ProductReviewDTO> getProductReview(long id) {
        log.debug("REST request to get ProductReview : {}", id);

        final ProductReviewDTO productReview = productReviewService.getProductReview(id);
        return ResponseEntity.ok().body(productReview);
    }

    /**
     * {@code POST  /productReviews} : Create a new productReview.
     *
     * @param productReviewDTO the productReviewDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productReviewDTO, or with status {@code 400 (Bad Request)} if the productReview has already an ID.
     */
    @PostMapping("/productReviews")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<ProductReviewDTO> createProductReview(@Valid @RequestBody ProductReviewDTO productReviewDTO) {
        log.debug("REST request to save ProductReview : {}", productReviewDTO);

        if (productReviewDTO.getId() != null) {
            return ResponseEntity.badRequest().build();
        }

        final ProductReviewDTO result = productReviewService.createProductReview(productReviewDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PUT  /productReviews} : Updates an existing productReview.
     *
     * @param productReviewDTO the productReviewDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productReviewDTO,
     * or with status {@code 400 (Bad Request)} if the productReviewDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productReviewDTO couldn't be updated.
     */
    @PutMapping("/productReviews")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<ProductReviewDTO> updateProductReview(@Valid @RequestBody ProductReviewDTO productReviewDTO) {
        log.debug("REST request to update ProductReview : {}", productReviewDTO);

        if (productReviewDTO.getId() == null) {
            return createProductReview(productReviewDTO);
        }

        final ProductReviewDTO result = productReviewService.updateProductReview(productReviewDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code DELETE  /productReviews/:id} : delete the "id" productReview.
     *
     * @param id the id of the productReviewDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/productReviews/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<Void> deleteProductReview(long id) {
        log.debug("REST request to delete ProductReview : {}", id);

        productReviewService.deleteProductReview(id);
        return ResponseEntity.noContent().build();
    }
}
