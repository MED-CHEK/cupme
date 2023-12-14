package com.cupme.service.dto;

import com.cupme.domain.Cart;
import com.cupme.domain.CartItem;
import com.cupme.domain.Product;
import com.cupme.domain.Protocol;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

public class CartItemDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private CartDTO cartDTO;

    private ProtocolCartDTO protocolCartDTO;

    private ProductCartDTO productCartDTO;

    private Integer quantity;

    private Instant createdDate;

    public CartItemDTO() {}

    public CartItemDTO(
        Long id,
        CartDTO cartDTO,
        ProtocolCartDTO protocolCartDTO,
        ProductCartDTO productCartDTO,
        Integer quantity,
        Instant createdDate
    ) {
        this.id = id;
        this.cartDTO = cartDTO;
        this.protocolCartDTO = protocolCartDTO;
        this.productCartDTO = productCartDTO;
        this.quantity = quantity;
        this.createdDate = createdDate;
    }

    public CartItemDTO(CartItem cartItem) {
        this.id = cartItem.getId();
        this.cartDTO = cartItem.getCart() == null ? null : new CartDTO(cartItem.getCart());
        this.protocolCartDTO = cartItem.getProtocol() == null ? null : new ProtocolCartDTO(cartItem.getProtocol());
        this.productCartDTO = cartItem.getProduct() == null ? null : new ProductCartDTO(cartItem.getProduct());
        this.quantity = cartItem.getQuantity();
        this.createdDate = cartItem.getCreatedDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CartDTO getCartDTO() {
        return cartDTO;
    }

    public void setCartDTO(CartDTO cartDTO) {
        this.cartDTO = cartDTO;
    }

    public ProtocolCartDTO getProtocolCartDTO() {
        return protocolCartDTO;
    }

    public void setProtocolCartDTO(ProtocolCartDTO protocolCartDTO) {
        this.protocolCartDTO = protocolCartDTO;
    }

    public ProductCartDTO getProductCartDTO() {
        return productCartDTO;
    }

    public void setProductCartDTO(ProductCartDTO productCartDTO) {
        this.productCartDTO = productCartDTO;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return (
            "CartItemDTO{" +
            "id=" +
            id +
            ", cartDTO=" +
            cartDTO +
            ", protocolCartDTO=" +
            protocolCartDTO +
            ", productCartDTO=" +
            productCartDTO +
            ", quantity=" +
            quantity +
            ", createdDate=" +
            createdDate +
            '}'
        );
    }
}
