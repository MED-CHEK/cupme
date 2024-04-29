package com.cupme.service.dto;

import com.cupme.domain.OrderItem;
import java.io.Serializable;

public class OrderItemDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private OrderDTO order;

    private ProtocolCartDTO protocol;

    private ProductCartDTO product;

    private Integer quantity;

    public OrderItemDTO() {}

    public OrderItemDTO(Long id, OrderDTO order, ProtocolCartDTO protocol, ProductCartDTO product, Integer quantity) {
        this.id = id;
        this.order = order;
        this.protocol = protocol;
        this.product = product;
        this.quantity = quantity;
    }

    public OrderItemDTO(OrderItem orderItem) {
        this.id = orderItem.getId();
        this.order = new OrderDTO(orderItem.getOrder());
        this.protocol = orderItem.getProtocol() != null ? new ProtocolCartDTO(orderItem.getProtocol()) : null;
        this.product = orderItem.getProduct() != null ? new ProductCartDTO(orderItem.getProduct()) : null;
        this.quantity = orderItem.getQuantity();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }

    public ProtocolCartDTO getProtocol() {
        return protocol;
    }

    public void setProtocol(ProtocolCartDTO protocol) {
        this.protocol = protocol;
    }

    public ProductCartDTO getProduct() {
        return product;
    }

    public void setProduct(ProductCartDTO product) {
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
            "OrderItemDTO{" +
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
