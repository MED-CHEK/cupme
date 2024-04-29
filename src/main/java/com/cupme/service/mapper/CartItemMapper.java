package com.cupme.service.mapper;

import com.cupme.domain.CartItem;
import com.cupme.service.dto.CartItemDTO;
import com.cupme.service.dto.CartItemDisplayDTO;
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

    private final ProtocolMapper protocolMapper;
    private final ProductMapper productMapper;

    private final CartMapper cartMapper;

    public CartItemMapper(ProtocolMapper protocolMapper, ProductMapper productMapper, CartMapper cartMapper) {
        this.protocolMapper = protocolMapper;
        this.productMapper = productMapper;
        this.cartMapper = cartMapper;
    }

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
            cartItem.setProduct(productMapper.productCartDTOToProduct(cartItemDTO.getProductCartDTO()));
            cartItem.setCart(cartMapper.cartDTOToCart(cartItemDTO.getCartDTO()));
            cartItem.setProtocol(protocolMapper.protocolCartDTOToProtocol(cartItemDTO.getProtocolCartDTO()));
            cartItem.setCreatedDate(cartItemDTO.getCreatedDate());

            return cartItem;
        }
    }

    public List<CartItemDisplayDTO> cartItemsToCartItemDisplayDTOs(List<CartItem> allByCartUserId) {
        return allByCartUserId.stream().filter(Objects::nonNull).map(this::cartItemToCartItemDisplayDTO).collect(Collectors.toList());
    }

    private CartItemDisplayDTO cartItemToCartItemDisplayDTO(CartItem cartItem) {
        CartItemDisplayDTO cartItemDisplayDTO = new CartItemDisplayDTO();

        cartItemDisplayDTO.setProtocol(cartItem.getProtocol() != null);
        cartItemDisplayDTO.setProductId(cartItem.getProtocol() != null ? cartItem.getProtocol().getId() : cartItem.getProduct().getId());
        cartItemDisplayDTO.setQuantity(cartItem.getQuantity());
        cartItemDisplayDTO.setName(cartItem.getProtocol() != null ? cartItem.getProtocol().getName() : cartItem.getProduct().getName());
        cartItemDisplayDTO.setPrice(cartItem.getProtocol() != null ? cartItem.getProtocol().getPrice() : cartItem.getProduct().getPrice());
        cartItemDisplayDTO.setPicture(
            cartItem.getProtocol() != null
                ? cartItem.getProtocol().getPictures().stream().filter(picture -> picture.getMain()).findFirst().get().getFile()
                : cartItem.getProduct().getPictures().stream().filter(picture -> picture.getMain()).findFirst().get().getFile()
        );
        cartItemDisplayDTO.setCreatedDate(cartItem.getCreatedDate());

        cartItemDisplayDTO.setCreatedDate(cartItem.getCreatedDate());
        return cartItemDisplayDTO;
    }
}
