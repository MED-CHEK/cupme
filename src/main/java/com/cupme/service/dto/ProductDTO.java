package com.cupme.service.dto;

import com.cupme.domain.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

public class ProductDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String type;
    private String shortDescription;
    private String description;
    private Double price;
    private Integer stock;
    private Instant lastModifiedDate;
    private Set<PictureDTO> pictures = new HashSet<>();

    public ProductDTO() {}

    public ProductDTO(
        Long id,
        String name,
        String type,
        String shortDescription,
        String description,
        Double price,
        Integer stock,
        Instant lastModifiedDate,
        Set<PictureDTO> pictures
    ) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.shortDescription = shortDescription;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.lastModifiedDate = lastModifiedDate;
        this.pictures = pictures;
    }

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.type = product.getType();
        this.shortDescription = product.getShortDescription();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.lastModifiedDate = product.getLastModifiedDate();
        this.pictures =
            product.getPictures() != null
                ? product.getPictures().stream().map(PictureDTO::new).collect(java.util.stream.Collectors.toSet())
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Set<PictureDTO> getPictures() {
        return pictures;
    }

    public void setPictures(Set<PictureDTO> pictures) {
        this.pictures = pictures;
    }

    @Override
    public String toString() {
        return (
            "Product{" +
            "id=" +
            id +
            ", name='" +
            name +
            '\'' +
            ", type='" +
            type +
            '\'' +
            ", shortDescription='" +
            shortDescription +
            '\'' +
            ", description='" +
            description +
            '\'' +
            ", price=" +
            price +
            ", stock=" +
            stock +
            ", lastModifiedDate=" +
            lastModifiedDate +
            ", pictures=" +
            pictures +
            "}"
        );
    }
}
