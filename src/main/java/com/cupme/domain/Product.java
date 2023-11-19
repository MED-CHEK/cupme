package com.cupme.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Other fields
    @NotNull
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "short_description", nullable = false)
    private String shortDescription;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "width", nullable = false)
    private Integer width;

    @Column(name = "size", nullable = false)
    private Integer size;

    @Column(name = "height", nullable = false)
    private Integer height;

    @Column(name = "weight", nullable = false)
    private Integer weight;

    @ManyToMany
    @JoinTable(
        name = "protocol_product",
        joinColumns = { @JoinColumn(name = "product_id", referencedColumnName = "id") },
        inverseJoinColumns = { @JoinColumn(name = "protocol_id", referencedColumnName = "id") }
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Protocol> protocols = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "product_tag",
        joinColumns = { @JoinColumn(name = "product_id", referencedColumnName = "id") },
        inverseJoinColumns = { @JoinColumn(name = "tag_id", referencedColumnName = "id") }
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Tag> tags = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "product_category",
        joinColumns = { @JoinColumn(name = "product_id", referencedColumnName = "id") },
        inverseJoinColumns = { @JoinColumn(name = "category_id", referencedColumnName = "id") }
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Category> categories = new HashSet<>();

    public Product() {}

    public Product(
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return (
            Objects.equals(getId(), product.getId()) &&
            Objects.equals(getName(), product.getName()) &&
            Objects.equals(getType(), product.getType()) &&
            Objects.equals(getShortDescription(), product.getShortDescription()) &&
            Objects.equals(getDescription(), product.getDescription()) &&
            Objects.equals(getPrice(), product.getPrice()) &&
            Objects.equals(getStock(), product.getStock()) &&
            Objects.equals(getWidth(), product.getWidth()) &&
            Objects.equals(getSize(), product.getSize()) &&
            Objects.equals(getHeight(), product.getHeight()) &&
            Objects.equals(getWeight(), product.getWeight()) &&
            Objects.equals(getProtocols(), product.getProtocols()) &&
            Objects.equals(getTags(), product.getTags()) &&
            Objects.equals(getCategories(), product.getCategories())
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            getId(),
            getName(),
            getType(),
            getShortDescription(),
            getDescription(),
            getPrice(),
            getStock(),
            getWidth(),
            getSize(),
            getHeight(),
            getWeight(),
            getProtocols(),
            getTags(),
            getCategories()
        );
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
