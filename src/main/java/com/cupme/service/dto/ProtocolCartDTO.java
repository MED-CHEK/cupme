package com.cupme.service.dto;

import com.cupme.domain.Protocol;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ProtocolCartDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;
    private Double price;
    private PictureDTO picture;

    public ProtocolCartDTO() {}

    public ProtocolCartDTO(Long id, String name, Double price, PictureDTO picture) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.picture = picture;
    }

    public ProtocolCartDTO(Protocol protocol) {
        this.id = protocol.getId();
        this.name = protocol.getName();
        this.price = protocol.getPrice();
        this.picture =
            protocol.getPictures() != null
                ? new PictureDTO(protocol.getPictures().stream().filter(p -> p.getMain()).collect(Collectors.toList()).get(0))
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
        return "ProtocolCartDTO{" + "id=" + id + ", name='" + name + '\'' + ", price=" + price + ", picture=" + picture + '}';
    }
}
