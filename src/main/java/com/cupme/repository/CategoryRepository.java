package com.cupme.repository;

import com.cupme.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link Category} entity.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {}
