package com.cupme.service.dto;

import com.cupme.domain.Session;
import java.io.Serializable;

public class SessionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private Double duration;
    private Double price;

    public SessionDTO() {}

    public SessionDTO(Long id, String name, Double duration, Double price) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.price = price;
    }

    public SessionDTO(Session session) {
        this.id = session.getId();
        this.name = session.getName();
        this.duration = session.getDuration();
        this.price = session.getPrice();
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

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "SessionDTO{" + "id=" + id + ", name='" + name + '\'' + ", duration=" + duration + ", price=" + price + '}';
    }
}
