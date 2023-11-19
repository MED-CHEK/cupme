package com.cupme.service.mapper;

import com.cupme.domain.Cart;
import com.cupme.service.dto.CartDTO;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * Mapper for the entity {@link Cart} and its DTO called {@link Cart}.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class CartMapper {

    public List<CartDTO> cartsToCartDTOs(List<Cart> carts) {
        return carts.stream().filter(Objects::nonNull).map(this::cartToCartDTO).collect(Collectors.toList());
    }

    public CartDTO cartToCartDTO(Cart cart) {
        return new CartDTO(cart);
    }

    public List<Cart> cartDTOsToCarts(List<CartDTO> cartDTOS) {
        return cartDTOS.stream().filter(Objects::nonNull).map(this::cartDTOToCart).collect(Collectors.toList());
    }

    public Cart cartDTOToCart(CartDTO cartDTO) {
        if (cartDTO == null) {
            return null;
        } else {
            Cart cart = new Cart();
            cart.setId(cartDTO.getId());
            cart.setUser(cartDTO.getUser());

            return cart;
        }
    }
}
