package com.cupme.repository;

import com.cupme.domain.Protocol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link Protocol} entity.
 */
@Repository
public interface ProtocolRepository extends JpaRepository<Protocol, Long> {}
