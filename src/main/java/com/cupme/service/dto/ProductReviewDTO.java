package com.cupme.service.dto;

import com.cupme.domain.Product;
import com.cupme.domain.ProductReview;
import com.cupme.domain.User;
import java.io.Serializable;

public class ProductReviewDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String comment;

    private Integer rating;

    private User user;

    private Product product;

    private String createdDate;

    public ProductReviewDTO() {}

    public ProductReviewDTO(Long id, String comment, Integer rating, User user, Product product, String createdDate) {
        this.id = id;
        this.comment = comment;
        this.rating = rating;
        this.user = user;
        this.product = product;
        this.createdDate = createdDate;
    }

    public ProductReviewDTO(ProductReview productReview) {
        this.id = productReview.getId();
        this.comment = productReview.getComment();
        this.rating = productReview.getRating();
        this.user = productReview.getUser();
        this.product = productReview.getProduct();
        this.createdDate = productReview.getCreatedDate();
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
