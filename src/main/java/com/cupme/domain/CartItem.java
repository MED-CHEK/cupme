package com.cupme.domain;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A cart item.
 */
@Entity
@Table(name = "cart_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CartItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "protocol_id")
    private Protocol protocol;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    public CartItem() {}

    public CartItem(Long id, Cart cart, Protocol protocol, Product product, Integer quantity, Instant createdDate) {
        this.id = id;
        this.cart = cart;
        this.protocol = protocol;
        this.product = product;
        this.quantity = quantity;
        this.createdDate = createdDate;
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

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartItem)) return false;
        CartItem cartItem = (CartItem) o;
        return (
            Objects.equals(getId(), cartItem.getId()) &&
            Objects.equals(getCart(), cartItem.getCart()) &&
            Objects.equals(getProtocol(), cartItem.getProtocol()) &&
            Objects.equals(getProduct(), cartItem.getProduct()) &&
            Objects.equals(getQuantity(), cartItem.getQuantity()) &&
            Objects.equals(getCreatedDate(), cartItem.getCreatedDate())
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCart(), getProtocol(), getProduct(), getQuantity(), getCreatedDate());
    }

    @Override
    public String toString() {
        return (
            "CartItem{" +
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
