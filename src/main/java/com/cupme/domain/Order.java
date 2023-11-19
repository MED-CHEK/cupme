package com.cupme.domain;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A order.
 */
@Entity
@Table(name = "order")
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

    @Column(name = "created_date", nullable = false)
    private String createdDate;

    public Order() {}

    public Order(Long id, User user, Boolean paid, String createdDate) {
        this.id = id;
        this.user = user;
        this.paid = paid;
        this.createdDate = createdDate;
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

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
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
            Objects.equals(getCreatedDate(), order.getCreatedDate())
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getPaid(), getCreatedDate());
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", user=" + user + ", paid=" + paid + ", createdDate='" + createdDate + '\'' + '}';
    }
}
