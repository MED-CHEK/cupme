package com.cupme.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

public class OrderServerDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Double totalPrice;

    private String transactionId;

    private List<OrderItemServerDTO> orderItemServerDTOs;

    public OrderServerDTO() {}

    public OrderServerDTO(Double totalPrice, String transactionId, List<OrderItemServerDTO> orderItemServerDTOs) {
        this.totalPrice = totalPrice;
        this.transactionId = transactionId;
        this.orderItemServerDTOs = orderItemServerDTOs;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
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
            "totalPrice=" +
            totalPrice +
            ", transactionId='" +
            transactionId +
            '\'' +
            ", orderItemServerDTOs=" +
            orderItemServerDTOs +
            '}'
        );
    }
}
