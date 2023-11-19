package com.cupme.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A review for a protocol.
 */
@Entity
@Table(name = "protocol_review")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProtocolReview implements Serializable {

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
    @JoinColumn(name = "protocol_id", nullable = false)
    private Protocol protocol;

    @Column(name = "created_date", nullable = false)
    private String createdDate;

    public ProtocolReview() {}

    public ProtocolReview(Long id, String comment, Integer rating, User user, Protocol protocol, String createdDate) {
        this.id = id;
        this.comment = comment;
        this.rating = rating;
        this.user = user;
        this.protocol = protocol;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProtocolReview)) return false;
        ProtocolReview that = (ProtocolReview) o;
        return (
            Objects.equals(getId(), that.getId()) &&
            Objects.equals(getComment(), that.getComment()) &&
            Objects.equals(getRating(), that.getRating()) &&
            Objects.equals(getUser(), that.getUser()) &&
            Objects.equals(getProtocol(), that.getProtocol()) &&
            Objects.equals(getCreatedDate(), that.getCreatedDate())
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getComment(), getRating(), getUser(), getProtocol(), getCreatedDate());
    }

    @Override
    public String toString() {
        return (
            "ProtocolReview{" +
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
