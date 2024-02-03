package com.cupme.repository;

import com.cupme.domain.Protocol;
import com.cupme.domain.enumeration.ProtocolType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link Protocol} entity.
 */
@Repository
public interface ProtocolRepository extends JpaRepository<Protocol, Long> {
    List<Protocol> findProtocolByType(ProtocolType protocolType);
}
