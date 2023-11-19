package com.cupme.service;

import com.cupme.repository.CartItemRepository;
import com.cupme.service.dto.CartItemDTO;
import com.cupme.service.mapper.CartItemMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing cart items.
 */
@Service
@Transactional
public class CartItemService {

    private final Logger log = LoggerFactory.getLogger(CartItemService.class);

    private final CartItemRepository cartItemRepository;

    private final CartItemMapper cartItemMapper;
    private final CacheManager cacheManager;

    public CartItemService(CartItemRepository cartItemRepository, CartItemMapper cartItemMapper, CacheManager cacheManager) {
        this.cartItemRepository = cartItemRepository;
        this.cartItemMapper = cartItemMapper;
        this.cacheManager = cacheManager;
    }

    public List<CartItemDTO> getCartItems() {
        return cartItemMapper.cartItemsToCartItemDTOs(cartItemRepository.findAll());
    }

    public CartItemDTO getCartItem(long id) {
        return cartItemMapper.cartItemToCartItemDTO(cartItemRepository.findById(id).get());
    }

    public CartItemDTO createCartItem(CartItemDTO cartItemDTO) {
        return cartItemMapper.cartItemToCartItemDTO(cartItemRepository.save(cartItemMapper.cartItemDTOToCartItem(cartItemDTO)));
    }

    public CartItemDTO updateCartItem(CartItemDTO cartItemDTO) {
        return cartItemMapper.cartItemToCartItemDTO(cartItemRepository.save(cartItemMapper.cartItemDTOToCartItem(cartItemDTO)));
    }

    public void deleteCartItem(long id) {
        cartItemRepository.deleteById(id);
    }
}
