package com.cupme.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedDate;

/**
 * A order.
 */
@Entity
@Table(name = "command")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "paid", nullable = false)
    private Boolean paid;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private Instant createdDate = Instant.now();

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @Column(name = "transaction_id", nullable = false)
    private String transactionId;

    public Order() {}

    public Order(Long id, User user, Boolean paid, Instant createdDate, Double totalPrice, String transactionId) {
        this.id = id;
        this.user = user;
        this.paid = paid;
        this.createdDate = createdDate;
        this.totalPrice = totalPrice;
        this.transactionId = transactionId;
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

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return (
            Objects.equals(getId(), order.getId()) &&
            Objects.equals(getUser(), order.getUser()) &&
            Objects.equals(getPaid(), order.getPaid()) &&
            Objects.equals(getCreatedDate(), order.getCreatedDate()) &&
            Objects.equals(getTotalPrice(), order.getTotalPrice()) &&
            Objects.equals(getTransactionId(), order.getTransactionId())
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getPaid(), getCreatedDate(), getTotalPrice(), getTransactionId());
    }

    @Override
    public String toString() {
        return (
            "Order{" +
            "id=" +
            id +
            ", user=" +
            user +
            ", paid=" +
            paid +
            ", createdDate='" +
            createdDate +
            '\'' +
            ", totalPrice='" +
            totalPrice +
            '\'' +
            ", transactionId='" +
            transactionId +
            '\'' +
            '}'
        );
    }
}
