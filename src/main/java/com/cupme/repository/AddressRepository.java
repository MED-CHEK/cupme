package com.cupme.repository;

import com.cupme.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link Address} entity.
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {}
