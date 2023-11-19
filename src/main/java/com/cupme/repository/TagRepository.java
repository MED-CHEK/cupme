package com.cupme.repository;

import com.cupme.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link Tag} entity.
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {}
