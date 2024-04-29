package com.cupme.repository;

import com.cupme.domain.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link Appointment} entity.
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {}
