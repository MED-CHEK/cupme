package com.cupme.service.dto;

import com.cupme.domain.enumeration.ProductType;
import java.io.Serializable;

public class OrderItemServerDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private ProductType type;

    private Long productId;

    private Integer quantity;

    private AppointmentInfo appointmentInfo;

    public OrderItemServerDTO() {}

    public OrderItemServerDTO(Long id, ProductType type, Long productId, Integer quantity, AppointmentInfo appointmentInfo) {
        this.id = id;
        this.type = type;
        this.productId = productId;
        this.quantity = quantity;
        this.appointmentInfo = appointmentInfo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public AppointmentInfo getAppointmentInfo() {
        return appointmentInfo;
    }

    public void setAppointmentInfo(AppointmentInfo appointmentInfo) {
        this.appointmentInfo = appointmentInfo;
    }

    @Override
    public String toString() {
        return (
            "OrderItemServer{" +
            "id=" +
            id +
            ", type=" +
            type +
            ", productId=" +
            productId +
            ", quantity=" +
            quantity +
            ", appointmentInfo=" +
            appointmentInfo +
            '}'
        );
    }
}
