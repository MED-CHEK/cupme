package com.cupme.service.dto;

import com.cupme.domain.Cart;
import com.cupme.domain.CartItem;
import com.cupme.domain.Product;
import com.cupme.domain.Protocol;
import java.io.Serializable;

public class CartItemDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Cart cart;

    private Protocol protocol;

    private Product product;

    private Integer quantity;

    private String createdDate;

    public CartItemDTO() {}

    public CartItemDTO(Long id, Cart cart, Protocol protocol, Product product, Integer quantity, String createdDate) {
        this.id = id;
        this.cart = cart;
        this.protocol = protocol;
        this.product = product;
        this.quantity = quantity;
        this.createdDate = createdDate;
    }

    public CartItemDTO(CartItem cartItem) {
        this.id = cartItem.getId();
        this.cart = cartItem.getCart();
        this.protocol = cartItem.getProtocol();
        this.product = cartItem.getProduct();
        this.quantity = cartItem.getQuantity();
        this.createdDate = cartItem.getCreatedDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return (
            "CartItemDTO{" +
            "id=" +
            id +
            ", cart=" +
            cart +
            ", protocol=" +
            protocol +
            ", product=" +
            product +
            ", quantity=" +
            quantity +
            ", createdDate='" +
            createdDate +
            '\'' +
            '}'
        );
    }
}
