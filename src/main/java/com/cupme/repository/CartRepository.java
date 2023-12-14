package com.cupme.repository;

import com.cupme.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link Cart} entity.
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(long id);
}
