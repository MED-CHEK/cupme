package com.cupme.service.dto;

import com.cupme.domain.Category;
import com.cupme.domain.Product;
import com.cupme.domain.Protocol;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class CategoryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private Set<Protocol> protocols = new HashSet<>();

    private Set<Product> products = new HashSet<>();

    public CategoryDTO() {}

    public CategoryDTO(Long id, String name, Set<Protocol> protocols, Set<Product> products) {
        this.id = id;
        this.name = name;
        this.protocols = protocols;
        this.products = products;
    }

    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.protocols = category.getProtocols();
        this.products = category.getProducts();
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

    public Set<Protocol> getProtocols() {
        return protocols;
    }

    public void setProtocols(Set<Protocol> protocols) {
        this.protocols = protocols;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "CategoryDTO{" + "id=" + id + ", name='" + name + '\'' + ", protocols=" + protocols + ", products=" + products + '}';
    }
}
