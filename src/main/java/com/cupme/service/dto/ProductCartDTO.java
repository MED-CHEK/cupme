package com.cupme.service.dto;

import com.cupme.domain.Category;
import com.cupme.domain.Product;
import com.cupme.domain.Protocol;
import com.cupme.domain.Tag;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class ProductCartDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private Double price;

    private PictureDTO picture;

    public ProductCartDTO() {}

    public ProductCartDTO(Long id, String name, Double price, PictureDTO picture) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.picture = picture;
    }

    public ProductCartDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.picture =
            product.getPictures() != null
                ? new PictureDTO(product.getPictures().stream().filter(p -> p.getMain()).findFirst().orElse(null))
                : null;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public PictureDTO getPicture() {
        return picture;
    }

    public void setPicture(PictureDTO picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "ProductCartDTO{" + "id=" + id + ", name='" + name + '\'' + ", price=" + price + ", picture=" + picture + '}';
    }
}
