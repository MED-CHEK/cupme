package com.cupme.service.dto;

import com.cupme.domain.Category;
import com.cupme.domain.Product;
import com.cupme.domain.Protocol;
import com.cupme.domain.Tag;
import java.io.Serializable;
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

    private Integer width;

    private Integer size;

    private Integer height;

    private Integer weight;

    private Set<Protocol> protocols = new HashSet<>();

    private Set<Tag> tags = new HashSet<>();

    private Set<Category> categories = new HashSet<>();

    public ProductDTO() {}

    public ProductDTO(
        Long id,
        String name,
        String type,
        String shortDescription,
        String description,
        Double price,
        Integer stock,
        Integer width,
        Integer size,
        Integer height,
        Integer weight,
        Set<Protocol> protocols,
        Set<Tag> tags,
        Set<Category> categories
    ) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.shortDescription = shortDescription;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.width = width;
        this.size = size;
        this.height = height;
        this.weight = weight;
        this.protocols = protocols;
        this.tags = tags;
        this.categories = categories;
    }

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.type = product.getType();
        this.shortDescription = product.getShortDescription();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.width = product.getWidth();
        this.size = product.getSize();
        this.height = product.getHeight();
        this.weight = product.getWeight();
        this.protocols = product.getProtocols();
        this.tags = product.getTags();
        this.categories = product.getCategories();
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

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Set<Protocol> getProtocols() {
        return protocols;
    }

    public void setProtocols(Set<Protocol> protocols) {
        this.protocols = protocols;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
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
            ", width=" +
            width +
            ", size=" +
            size +
            ", height=" +
            height +
            ", weight=" +
            weight +
            ", protocols=" +
            protocols +
            ", tags=" +
            tags +
            ", categories=" +
            categories +
            '}'
        );
    }
}
