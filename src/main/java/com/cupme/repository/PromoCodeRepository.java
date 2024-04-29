package com.cupme.repository;

import com.cupme.domain.PromoCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link PromoCode} entity.
 */
@Repository
public interface PromoCodeRepository extends JpaRepository<PromoCode, Long> {
    int countAllByCode(String code);

    PromoCode findByCode(String code);
}
