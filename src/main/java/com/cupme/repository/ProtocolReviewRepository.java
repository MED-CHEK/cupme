package com.cupme.repository;

import com.cupme.domain.ProtocolReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link ProtocolReview} entity.
 */
@Repository
public interface ProtocolReviewRepository extends JpaRepository<ProtocolReview, Long> {}
