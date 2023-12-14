package com.cupme.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A transaction.
 */
@Entity
@Table(name = "picture")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String file;

    @NotNull
    @Column(name = "main")
    private Boolean main;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "protocol_id")
    private Protocol protocol;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Picture() {}

    public Picture(Long id, String name, String file, Boolean main, Protocol protocol, Product product) {
        this.id = id;
        this.name = name;
        this.file = file;
        this.main = main;
        this.protocol = protocol;
        this.product = product;
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

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public void setMain(Boolean main) {
        this.main = main;
    }

    public Boolean getMain() {
        return main;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "Picture{" + "id=" + id + ", name='" + name + '\'' + ", file='" + file + '\'' + ", main=" + main + '}';
    }
}
