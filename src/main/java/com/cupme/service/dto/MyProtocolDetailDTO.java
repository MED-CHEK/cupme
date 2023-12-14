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

    public MyProtocolDetailDTO() {}

    public MyProtocolDetailDTO(Long id, String name, Integer poseTime) {
        this.id = id;
        this.name = name;
        this.poseTime = poseTime;
    }

    public MyProtocolDetailDTO(Protocol protocol) {
        this.id = protocol.getId();
        this.name = protocol.getName();
        this.poseTime = protocol.getPoseTime();
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

    @Override
    public String toString() {
        return "ProtocolDetailDTO{" + "id=" + id + ", name='" + name + '\'' + ", poseTime=" + poseTime + '}';
    }
}
