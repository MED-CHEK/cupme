package com.cupme.service.dto;

import java.io.Serializable;
import java.time.Instant;

public class CartItemDisplayDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long productId;

    private String name;

    private Double price;

    private String picture;

    private boolean protocol;
    private Integer quantity;

    private Instant createdDate;

    public CartItemDisplayDTO() {}

    public CartItemDisplayDTO(
        Long productId,
        String name,
        Double price,
        String picture,
        boolean protocol,
        Integer quantity,
        Instant createdDate
    ) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.picture = picture;
        this.protocol = protocol;
        this.quantity = quantity;
        this.createdDate = createdDate;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public boolean isProtocol() {
        return protocol;
    }

    public void setProtocol(boolean protocol) {
        protocol = protocol;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return (
            "CartItemDisplayDTO{" +
            "productId=" +
            productId +
            ", name='" +
            name +
            '\'' +
            ", price=" +
            price +
            ", picture='" +
            picture +
            '\'' +
            ", isProtocol=" +
            protocol +
            ", quantity=" +
            quantity +
            ", createdDate=" +
            createdDate +
            '}'
        );
    }
}
