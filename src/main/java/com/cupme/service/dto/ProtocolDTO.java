package com.cupme.service.dto;

import com.cupme.domain.Category;
import com.cupme.domain.Product;
import com.cupme.domain.Protocol;
import com.cupme.domain.Tag;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ProtocolDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String type;

    private String shortDescription;

    private String description;

    private Double price;

    private Set<Product> products = new HashSet<>();

    private Set<Category> categories = new HashSet<>();

    private Set<Tag> tags = new HashSet<>();

    public ProtocolDTO() {}

    public ProtocolDTO(
        Long id,
        String name,
        String type,
        String shortDescription,
        String description,
        Double price,
        Set<Product> products,
        Set<Category> categories,
        Set<Tag> tags
    ) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.shortDescription = shortDescription;
        this.description = description;
        this.price = price;
        this.products = products;
        this.categories = categories;
        this.tags = tags;
    }

    public ProtocolDTO(Protocol protocol) {
        this.id = protocol.getId();
        this.name = protocol.getName();
        this.type = protocol.getType();
        this.shortDescription = protocol.getShortDescription();
        this.description = protocol.getDescription();
        this.price = protocol.getPrice();
        this.products = protocol.getProducts();
        this.tags = protocol.getTags();
        this.categories = protocol.getCategories();
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

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return (
            "Protocol{" +
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
            ", products=" +
            products +
            ", tags=" +
            tags +
            ", categories=" +
            categories +
            '}'
        );
    }
}
