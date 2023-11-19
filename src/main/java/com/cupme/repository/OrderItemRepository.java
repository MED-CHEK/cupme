package com.cupme.repository;

import com.cupme.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link OrderItem} entity.
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {}
