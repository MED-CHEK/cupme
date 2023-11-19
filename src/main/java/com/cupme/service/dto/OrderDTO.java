package com.cupme.service.dto;

import com.cupme.domain.Order;
import com.cupme.domain.User;
import java.io.Serializable;

public class OrderDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private User user;

    private Boolean paid;

    private String createdDate;

    public OrderDTO() {}

    public OrderDTO(Long id, User user, Boolean paid, String createdDate) {
        this.id = id;
        this.user = user;
        this.paid = paid;
        this.createdDate = createdDate;
    }

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.user = order.getUser();
        this.paid = order.getPaid();
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

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", user=" + user + ", paid=" + paid + ", createdDate='" + createdDate + '\'' + '}';
    }
}
