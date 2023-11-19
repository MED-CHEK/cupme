package com.cupme.service.dto;

import com.cupme.domain.Product;
import com.cupme.domain.Protocol;
import com.cupme.domain.Tag;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class TagDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private Set<Protocol> protocols = new HashSet<>();

    private Set<Product> products = new HashSet<>();

    public TagDTO() {}

    public TagDTO(Long id, String name, Set<Protocol> protocols, Set<Product> products) {
        this.id = id;
        this.name = name;
        this.protocols = protocols;
        this.products = products;
    }

    public TagDTO(Tag tag) {
        this.id = tag.getId();
        this.name = tag.getName();
        this.protocols = tag.getProtocols();
        this.products = tag.getProducts();
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
        return "Tag{" + "id=" + id + ", name='" + name + '\'' + ", protocols=" + protocols + ", products=" + products + '}';
    }
}
