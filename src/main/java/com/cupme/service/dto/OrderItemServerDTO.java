package com.cupme.service.dto;

import com.cupme.domain.enumeration.ProductType;
import java.io.Serializable;

public class OrderItemServerDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private ProductType type;

    private Long productId;

    private Integer quantity;

    public OrderItemServerDTO() {}

    public OrderItemServerDTO(ProductType type, Long productId, Integer quantity) {
        this.type = type;
        this.productId = productId;
        this.quantity = quantity;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderItemServerDTO{" + "type=" + type + ", productId=" + productId + ", quantity=" + quantity + '}';
    }
}
