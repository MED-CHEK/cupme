package com.cupme.service.dto;

import com.cupme.domain.Picture;
import com.cupme.domain.Product;
import com.cupme.domain.Protocol;
import java.io.Serializable;

/**
 * A DTO representing a user, with only the public attributes.
 */
public class PictureDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String file;

    private Boolean main;

    private Protocol protocol;

    private Product product;

    public PictureDTO() {}

    public PictureDTO(Long id, String name, String file, Boolean main, Protocol protocol, Product product) {
        this.id = id;
        this.name = name;
        this.file = file;
        this.main = main;
        this.protocol = protocol;
        this.product = product;
    }

    public PictureDTO(Picture picture) {
        this.id = picture.getId();
        this.name = picture.getName();
        this.file = picture.getFile();
        this.main = picture.getMain();
        this.protocol = picture.getProtocol();
        this.product = picture.getProduct();
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

    public Boolean getMain() {
        return main;
    }

    public void setMain(Boolean main) {
        this.main = main;
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
        return (
            "PictureDTO{" +
            "id=" +
            id +
            ", name='" +
            name +
            '\'' +
            ", file='" +
            file +
            '\'' +
            ", main=" +
            main +
            ", protocol=" +
            protocol +
            ", product=" +
            product +
            '}'
        );
    }
}
