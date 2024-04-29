package com.cupme.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedDate;

/**
 * A cart item.
 */
@Entity
@Table(name = "session")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Session implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "duration")
    private Double duration;

    @Column(name = "price", nullable = false)
    private Double price;

    public Session() {}

    public Session(Long id, String name, Double duration, Double price) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Session)) return false;
        Session session = (Session) o;
        return (
            Objects.equals(getId(), session.getId()) &&
            Objects.equals(getName(), session.getName()) &&
            Objects.equals(getDuration(), session.getDuration()) &&
            Objects.equals(getPrice(), session.getPrice())
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDuration(), getPrice());
    }

    @Override
    public String toString() {
        return "Session{" + "id=" + id + ", name='" + name + '\'' + ", duration=" + duration + ", price=" + price + '}';
    }
}
