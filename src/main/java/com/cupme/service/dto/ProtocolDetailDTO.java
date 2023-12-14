package com.cupme.service.dto;

import com.cupme.domain.Protocol;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ProtocolDetailDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String type;

    private String shortDescription;

    private String description;

    private Double price;

    private Integer poseTime;

    private Set<ProductCartDTO> productDTOs = new HashSet<>();

    private Set<CategoryDTO> categoryDTOs = new HashSet<>();

    private Set<TagDTO> tagDTOs = new HashSet<>();

    private PictureDTO picture;

    public ProtocolDetailDTO() {}

    public ProtocolDetailDTO(
        Long id,
        String name,
        String type,
        String shortDescription,
        String description,
        Double price,
        Integer poseTime,
        Set<ProductCartDTO> productDTOs,
        Set<CategoryDTO> categoryDTOs,
        Set<TagDTO> tagDTOs,
        PictureDTO picture
    ) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.shortDescription = shortDescription;
        this.description = description;
        this.price = price;
        this.poseTime = poseTime;
        this.productDTOs = productDTOs;
        this.categoryDTOs = categoryDTOs;
        this.tagDTOs = tagDTOs;
        this.picture = picture;
    }

    public ProtocolDetailDTO(Protocol protocol) {
        this.id = protocol.getId();
        this.name = protocol.getName();
        this.type = protocol.getType();
        this.shortDescription = protocol.getShortDescription();
        this.description = protocol.getDescription();
        this.price = protocol.getPrice();
        this.poseTime = protocol.getPoseTime();
        this.productDTOs =
            protocol.getProducts() != null ? protocol.getProducts().stream().map(ProductCartDTO::new).collect(Collectors.toSet()) : null;
        this.tagDTOs = protocol.getTags() != null ? protocol.getTags().stream().map(TagDTO::new).collect(Collectors.toSet()) : null;
        this.categoryDTOs =
            protocol.getCategories() != null ? protocol.getCategories().stream().map(CategoryDTO::new).collect(Collectors.toSet()) : null;
        this.picture =
            protocol.getPictures() != null
                ? protocol.getPictures().stream().filter(p -> p.getMain()).findFirst().map(PictureDTO::new).orElse(null)
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

    public Integer getPoseTime() {
        return poseTime;
    }

    public void setPoseTime(Integer poseTime) {
        this.poseTime = poseTime;
    }

    public Set<ProductCartDTO> getProductDTOs() {
        return productDTOs;
    }

    public void setProductDTOs(Set<ProductCartDTO> productDTOs) {
        this.productDTOs = productDTOs;
    }

    public Set<CategoryDTO> getCategoryDTOs() {
        return categoryDTOs;
    }

    public void setCategoryDTOs(Set<CategoryDTO> categoryDTOs) {
        this.categoryDTOs = categoryDTOs;
    }

    public Set<TagDTO> getTagDTOs() {
        return tagDTOs;
    }

    public void setTagDTOs(Set<TagDTO> tagDTOs) {
        this.tagDTOs = tagDTOs;
    }

    public PictureDTO getPicture() {
        return picture;
    }

    public void setPicture(PictureDTO picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return (
            "ProtocolDetailDTO{" +
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
            ", poseTime=" +
            poseTime +
            ", productDTOs=" +
            productDTOs +
            ", categoryDTOs=" +
            categoryDTOs +
            ", tagDTOs=" +
            tagDTOs +
            ", picture=" +
            picture +
            '}'
        );
    }
}
