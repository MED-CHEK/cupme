package com.cupme.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A review for a product.
 */
@Entity
@Table(name = "product_review")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductReview implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Other fields
    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "created_date", nullable = false)
    private String createdDate;

    public ProductReview() {}

    public ProductReview(Long id, String comment, Integer rating, User user, Product product, String createdDate) {
        this.id = id;
        this.comment = comment;
        this.rating = rating;
        this.user = user;
        this.product = product;
        this.createdDate = createdDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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
        if (!(o instanceof ProductReview)) return false;
        ProductReview that = (ProductReview) o;
        return (
            Objects.equals(getId(), that.getId()) &&
            Objects.equals(getComment(), that.getComment()) &&
            Objects.equals(getRating(), that.getRating()) &&
            Objects.equals(getUser(), that.getUser()) &&
            Objects.equals(getProduct(), that.getProduct()) &&
            Objects.equals(getCreatedDate(), that.getCreatedDate())
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getComment(), getRating(), getUser(), getProduct(), getCreatedDate());
    }

    @Override
    public String toString() {
        return (
            "ProductReview{" +
            "id=" +
            id +
            ", comment='" +
            comment +
            '\'' +
            ", rating=" +
            rating +
            ", user=" +
            user +
            ", product=" +
            product +
            ", createdDate='" +
            createdDate +
            '\'' +
            '}'
        );
    }
}
