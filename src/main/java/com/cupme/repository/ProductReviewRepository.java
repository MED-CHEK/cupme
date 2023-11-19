package com.cupme.repository;

import com.cupme.domain.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link ProductReview} entity.
 */
@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {}
