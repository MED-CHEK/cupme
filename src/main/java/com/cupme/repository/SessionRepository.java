package com.cupme.repository;

import com.cupme.domain.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link Session} entity.
 */
@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {}
