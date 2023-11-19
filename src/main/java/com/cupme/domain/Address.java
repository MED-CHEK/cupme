package com.cupme.domain;

import com.cupme.domain.enumeration.AdressType;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "address")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "postal_address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "postal_code")
    private String postalCode;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private AdressType type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Address() {}

    public Address(Long id, String address, String city, String country, String postalCode, AdressType type, User user) {
        this.id = id;
        this.address = address;
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
        this.type = type;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public AdressType getType() {
        return type;
    }

    public void setType(AdressType type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return (
            "Address{" +
            "id=" +
            id +
            ", address='" +
            address +
            '\'' +
            ", city='" +
            city +
            '\'' +
            ", country='" +
            country +
            '\'' +
            ", postalCode='" +
            postalCode +
            '\'' +
            ", type='" +
            type +
            '\'' +
            '}'
        );
    }
}
