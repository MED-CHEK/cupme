package com.cupme.service.mapper;

import com.cupme.domain.CartItem;
import com.cupme.service.dto.CartItemDTO;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * Mapper for the entity {@link CartItem} and its DTO called {@link CartItem}.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class CartItemMapper {

    public List<CartItemDTO> cartItemsToCartItemDTOs(List<CartItem> cartItems) {
        return cartItems.stream().filter(Objects::nonNull).map(this::cartItemToCartItemDTO).collect(Collectors.toList());
    }

    public CartItemDTO cartItemToCartItemDTO(CartItem cartItem) {
        return new CartItemDTO(cartItem);
    }

    public List<CartItem> cartItemDTOsToCartItems(List<CartItemDTO> cartItemDTOS) {
        return cartItemDTOS.stream().filter(Objects::nonNull).map(this::cartItemDTOToCartItem).collect(Collectors.toList());
    }

    public CartItem cartItemDTOToCartItem(CartItemDTO cartItemDTO) {
        if (cartItemDTO == null) {
            return null;
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setId(cartItemDTO.getId());
            cartItem.setQuantity(cartItemDTO.getQuantity());
            cartItem.setProduct(cartItemDTO.getProduct());
            cartItem.setCart(cartItemDTO.getCart());
            cartItem.setProtocol(cartItemDTO.getProtocol());
            cartItem.setCreatedDate(cartItemDTO.getCreatedDate());

            return cartItem;
        }
    }
}
