package com.cupme.service.dto;

import com.cupme.domain.PromoCode;
import java.io.Serializable;
import java.time.Instant;

public class PromoCodeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String code;
    private Double discount;
    private Instant createdDate;
    private boolean activated = false;

    public PromoCodeDTO() {}

    public PromoCodeDTO(Long id, String code, Double discount, Instant createdDate, boolean activated) {
        this.id = id;
        this.code = code;
        this.discount = discount;
        this.createdDate = createdDate;
        this.activated = activated;
    }

    public PromoCodeDTO(PromoCode promoCode) {
        this.id = promoCode.getId();
        this.code = promoCode.getCode();
        this.discount = promoCode.getDiscount();
        this.createdDate = promoCode.getCreatedDate();
        this.activated = promoCode.isActivated();
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
    public String toString() {
        return (
            "PromoCodeDTO{" +
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
