package com.cupme.service.dto;

import com.cupme.domain.Order;
import com.cupme.domain.User;
import java.io.Serializable;
import java.time.Instant;

public class OrderDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private User user;

    private Boolean paid;

    private Double totalPrice;

    private String transactionId;

    private Instant createdDate;

    public OrderDTO() {}

    public OrderDTO(Long id, User user, Boolean paid, Double totalPrice, String transactionId, Instant createdDate) {
        this.id = id;
        this.user = user;
        this.paid = paid;
        this.totalPrice = totalPrice;
        this.transactionId = transactionId;
        this.createdDate = createdDate;
    }

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.user = order.getUser();
        this.paid = order.getPaid();
        this.totalPrice = order.getTotalPrice();
        this.transactionId = order.getTransactionId();
        this.createdDate = order.getCreatedDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean isPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
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

    public Boolean getPaid() {
        return paid;
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
            "OrderDTO{" +
            "id=" +
            id +
            ", user=" +
            user +
            ", paid=" +
            paid +
            ", totalPrice=" +
            totalPrice +
            ", transactionId='" +
            transactionId +
            '\'' +
            ", createdDate=" +
            createdDate +
            '}'
        );
    }
}
