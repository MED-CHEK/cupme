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
@Table(name = "promo_code")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PromoCode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "discount")
    private Double discount;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private Instant createdDate = Instant.now();

    @NotNull
    @Column(nullable = false)
    private boolean activated = false;

    public PromoCode() {}

    public PromoCode(Long id, String code, Double discount, Instant createdDate, boolean activated) {
        this.id = id;
        this.code = code;
        this.discount = discount;
        this.createdDate = createdDate;
        this.activated = activated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PromoCode)) return false;
        PromoCode promoCode = (PromoCode) o;
        return (
            isActivated() == promoCode.isActivated() &&
            Objects.equals(getId(), promoCode.getId()) &&
            Objects.equals(getCode(), promoCode.getCode()) &&
            Objects.equals(getDiscount(), promoCode.getDiscount()) &&
            Objects.equals(getCreatedDate(), promoCode.getCreatedDate())
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCode(), getDiscount(), getCreatedDate(), isActivated());
    }

    @Override
    public String toString() {
        return (
            "PromoCode{" +
            "id=" +
            id +
            ", code='" +
            code +
            '\'' +
            ", discount=" +
            discount +
            ", createdDate=" +
            createdDate +
            ", activated=" +
            activated +
            '}'
        );
    }
}
