package com.cupme.repository;

import com.cupme.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link Order} entity.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {}
