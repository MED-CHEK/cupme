package com.cupme.service.dto;

import com.cupme.domain.Order;
import com.cupme.domain.OrderItem;
import com.cupme.domain.Product;
import com.cupme.domain.Protocol;
import java.io.Serializable;

public class OrderItemDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Order order;

    private Protocol protocol;

    private Product product;

    private Integer quantity;

    public OrderItemDTO() {}

    public OrderItemDTO(Long id, Order order, Protocol protocol, Product product, Integer quantity) {
        this.id = id;
        this.order = order;
        this.protocol = protocol;
        this.product = product;
        this.quantity = quantity;
    }

    public OrderItemDTO(OrderItem orderItem) {
        this.id = orderItem.getId();
        this.order = orderItem.getOrder();
        this.protocol = orderItem.getProtocol();
        this.product = orderItem.getProduct();
        this.quantity = orderItem.getQuantity();
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
