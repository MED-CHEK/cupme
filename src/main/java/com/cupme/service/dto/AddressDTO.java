package com.cupme.service.dto;

import com.cupme.domain.Address;
import com.cupme.domain.User;
import com.cupme.domain.enumeration.AdressType;
import java.io.Serializable;

public class AddressDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String address;

    private String city;

    private String country;

    private String postalCode;

    private AdressType type;

    private User user;

    public AddressDTO() {}

    public AddressDTO(Long id, String address, String city, String country, String postalCode, AdressType type, User user) {
        this.id = id;
        this.address = address;
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
        this.type = type;
        this.user = user;
    }

    public AddressDTO(Address address) {
        this.id = address.getId();
        this.address = address.getAddress();
        this.city = address.getCity();
        this.country = address.getCountry();
        this.postalCode = address.getPostalCode();
        this.type = address.getType();
        this.user = address.getUser();
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
            "AddressDTO{" +
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
