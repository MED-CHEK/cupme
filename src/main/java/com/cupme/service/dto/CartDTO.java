package com.cupme.service.dto;

import com.cupme.domain.Cart;
import com.cupme.domain.User;
import java.io.Serializable;

public class CartDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private PublicUserDTO user;

    public CartDTO() {}

    public CartDTO(Long id, PublicUserDTO user) {
        this.id = id;
        this.user = user;
    }

    public CartDTO(Cart cart) {
        this.id = cart.getId();
        this.user = new PublicUserDTO(cart.getUser());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PublicUserDTO getUser() {
        return user;
    }

    public void setUser(PublicUserDTO user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "CartDTO{" + "id=" + id + ", user=" + user + '}';
    }
}
