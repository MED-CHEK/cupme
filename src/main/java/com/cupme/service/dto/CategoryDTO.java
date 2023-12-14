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

    public CategoryDTO() {}

    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
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
        return "CategoryDTO{" + "id=" + id + ", name='" + name + '}';
    }
}
