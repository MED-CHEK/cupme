package com.cupme.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A order item.
 */
@Entity
@Table(name = "order_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "protocol_id", nullable = false)
    private Protocol protocol;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    public OrderItem() {}

    public OrderItem(Long id, Order order, Protocol protocol, Product product, Integer quantity) {
        this.id = id;
        this.order = order;
        this.protocol = protocol;
        this.product = product;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem)) return false;
        OrderItem orderItem = (OrderItem) o;
        return (
            Objects.equals(getId(), orderItem.getId()) &&
            Objects.equals(getOrder(), orderItem.getOrder()) &&
            Objects.equals(getProtocol(), orderItem.getProtocol()) &&
            Objects.equals(getProduct(), orderItem.getProduct()) &&
            Objects.equals(getQuantity(), orderItem.getQuantity())
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getOrder(), getProtocol(), getProduct(), getQuantity());
    }

    @Override
    public String toString() {
        return (
            "OrderItem{" +
            "id=" +
            id +
            ", order=" +
            order +
            ", protocol=" +
            protocol +
            ", product=" +
            product +
            ", quantity=" +
            quantity +
            '}'
        );
    }
}
