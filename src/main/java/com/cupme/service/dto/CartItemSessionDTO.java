package com.cupme.service.dto;

import com.cupme.domain.enumeration.ProductType;
import java.io.Serializable;
import java.time.Instant;

public class CartItemSessionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long productId;
    private ProductType type;
    private Integer quantity;
    private Instant createdDate;

    public CartItemSessionDTO() {}

    public CartItemSessionDTO(Long productId, ProductType type, Integer quantity, Instant createdDate) {
        this.productId = productId;
        this.type = type;
        this.quantity = quantity;
        this.createdDate = createdDate;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
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
            "CartItemSessionDTO{" +
            "productId=" +
            productId +
            ", type=" +
            type +
            ", quantity=" +
            quantity +
            ", createdDate=" +
            createdDate +
            '}'
        );
    }
}
