package com.cupme.service.dto;

import com.cupme.domain.Protocol;
import com.cupme.domain.ProtocolReview;
import com.cupme.domain.User;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class ProtocolReviewDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String comment;

    private Integer rating;

    private User user;

    private Protocol protocol;

    private String createdDate;

    public ProtocolReviewDTO() {}

    public ProtocolReviewDTO(Long id, String comment, Integer rating, User user, Protocol protocol, String createdDate) {
        this.id = id;
        this.comment = comment;
        this.rating = rating;
        this.user = user;
        this.protocol = protocol;
        this.createdDate = createdDate;
    }

    public ProtocolReviewDTO(ProtocolReview protocolReview) {
        this.id = protocolReview.getId();
        this.comment = protocolReview.getComment();
        this.rating = protocolReview.getRating();
        this.user = protocolReview.getUser();
        this.protocol = protocolReview.getProtocol();
        this.createdDate = protocolReview.getCreatedDate();
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

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
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
            "Review{" +
            "id=" +
            id +
            ", comment='" +
            comment +
            '\'' +
            ", rating=" +
            rating +
            ", user=" +
            user +
            ", protocol=" +
            protocol +
            ", createdDate='" +
            createdDate +
            '\'' +
            '}'
        );
    }
}
