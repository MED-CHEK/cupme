package com.cupme.service.dto;

import java.io.Serializable;
import java.util.List;

public class OrderServerDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long orderId;
    private Long userId;
    private Double totalPrice;
    private boolean paid;

    private String transactionId;

    private List<OrderItemServerDTO> orderItemServerDTOs;

    public OrderServerDTO() {}

    public OrderServerDTO(
        Long orderId,
        Long userId,
        Double totalPrice,
        boolean paid,
        String transactionId,
        List<OrderItemServerDTO> orderItemServerDTOs
    ) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.paid = paid;
        this.transactionId = transactionId;
        this.orderItemServerDTOs = orderItemServerDTOs;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public List<OrderItemServerDTO> getOrderItemServerDTOs() {
        return orderItemServerDTOs;
    }

    public void setOrderItemServerDTOs(List<OrderItemServerDTO> orderItemServerDTOs) {
        this.orderItemServerDTOs = orderItemServerDTOs;
    }

    @Override
    public String toString() {
        return (
            "OrderServerDTO{" +
            "orderId=" +
            orderId +
            ", userId=" +
            userId +
            ", totalPrice=" +
            totalPrice +
            ", paid=" +
            paid +
            ", transactionId='" +
            transactionId +
            '\'' +
            ", orderItemServerDTOs=" +
            orderItemServerDTOs +
            '}'
        );
    }
}
