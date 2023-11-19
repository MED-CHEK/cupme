package com.cupme.repository;

import com.cupme.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link CartItem} entity.
 */
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {}
