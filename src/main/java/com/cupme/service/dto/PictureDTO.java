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

    public PictureDTO() {}

    public PictureDTO(Long id, String name, String file, Boolean main) {
        this.id = id;
        this.name = name;
        this.file = file;
        this.main = main;
    }

    public PictureDTO(Picture picture) {
        this.id = picture.getId();
        this.name = picture.getName();
        this.file = picture.getFile();
        this.main = picture.getMain();
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

    @Override
    public String toString() {
        return ("PictureDTO{" + "id=" + id + ", name='" + name + '\'' + ", file='" + file + '\'' + ", main=" + main + '}');
    }
}
