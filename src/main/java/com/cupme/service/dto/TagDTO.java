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

    public TagDTO() {}

    public TagDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public TagDTO(Tag tag) {
        this.id = tag.getId();
        this.name = tag.getName();
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

    @Override
    public String toString() {
        return "Tag{" + "id=" + id + ", name='" + name + '}';
    }
}
