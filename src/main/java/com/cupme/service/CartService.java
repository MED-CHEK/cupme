package com.cupme.service;

import com.cupme.repository.CartRepository;
import com.cupme.service.dto.CartDTO;
import com.cupme.service.mapper.CartMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing carts.
 */
@Service
@Transactional
public class CartService {

    private final Logger log = LoggerFactory.getLogger(CartService.class);

    private final CartRepository cartRepository;

    private final CartMapper cartMapper;
    private final CacheManager cacheManager;

    private final UserService userService;

    public CartService(CartRepository cartRepository, CartMapper cartMapper, CacheManager cacheManager, UserService userService) {
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
        this.cacheManager = cacheManager;
        this.userService = userService;
    }

    public CartDTO getCartForUser() {
        Long userId = userService.getUserWithAuthorities().get().getId();
        return cartMapper.cartToCartDTO(cartRepository.findByUserId(userId));
    }

    public List<CartDTO> getCarts() {
        return cartMapper.cartsToCartDTOs(cartRepository.findAll());
    }

    public CartDTO getCart(long id) {
        return cartMapper.cartToCartDTO(cartRepository.findById(id).get());
    }

    public CartDTO createCart(CartDTO cartDTO) {
        return cartMapper.cartToCartDTO(cartRepository.save(cartMapper.cartDTOToCart(cartDTO)));
    }

    public CartDTO updateCart(CartDTO cartDTO) {
        return cartMapper.cartToCartDTO(cartRepository.save(cartMapper.cartDTOToCart(cartDTO)));
    }

    public void deleteCart(long id) {
        cartRepository.deleteById(id);
    }
}
