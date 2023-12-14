package com.cupme.repository;

import com.cupme.domain.CartItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link CartItem} entity.
 */
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    int countAllByCartUserId(long id);
    List<CartItem> findAllByCartUserId(long id);
    CartItem findByCartUserId(long id);

    CartItem findByCartIdAndProductId(long userId, long productId);

    CartItem findByCartIdAndProtocolId(long userId, long productId);
    void deleteAllByCartId(Long id);
}
