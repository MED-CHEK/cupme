package com.cupme.service.dto;

import com.cupme.domain.Protocol;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class MyProtocolDetailDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private Integer poseTime;
    private Integer nbPoseTime;
    private ProductCartDTO productDTO;

    public MyProtocolDetailDTO() {}

    public MyProtocolDetailDTO(Long id, String name, Integer poseTime, Integer nbPoseTime, ProductCartDTO productDTO) {
        this.id = id;
        this.name = name;
        this.poseTime = poseTime;
        this.nbPoseTime = nbPoseTime;
        this.productDTO = productDTO;
    }

    public MyProtocolDetailDTO(Protocol protocol) {
        this.id = protocol.getId();
        this.name = protocol.getName();
        this.poseTime = protocol.getPoseTime();
        this.productDTO =
            protocol.getProducts() != null ? protocol.getProducts().stream().map(ProductCartDTO::new).findFirst().get() : null;
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

    public Integer getPoseTime() {
        return poseTime;
    }

    public void setPoseTime(Integer poseTime) {
        this.poseTime = poseTime;
    }

    public Integer getNbPoseTime() {
        return nbPoseTime;
    }

    public void setNbPoseTime(Integer nbPoseTime) {
        this.nbPoseTime = nbPoseTime;
    }

    public ProductCartDTO getProductDTO() {
        return productDTO;
    }

    public void setProductDTO(ProductCartDTO productDTO) {
        this.productDTO = productDTO;
    }

    @Override
    public String toString() {
        return (
            "MyProtocolDetailDTO{" +
            "id=" +
            id +
            ", name='" +
            name +
            '\'' +
            ", poseTime=" +
            poseTime +
            ", nbPoseTime=" +
            nbPoseTime +
            ", productDTO=" +
            productDTO +
            '}'
        );
    }
}
