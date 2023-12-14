package com.cupme.repository;

import com.cupme.domain.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link Picture} entity.
 */
@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {}
