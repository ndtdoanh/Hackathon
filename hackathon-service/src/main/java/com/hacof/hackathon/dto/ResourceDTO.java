package com.hacof.hackathon.dto;

import com.hacof.hackathon.constant.ResourceStatus;
import com.hacof.hackathon.constant.ResourceType;

import lombok.Data;

@Data
public class ResourceDTO {
    private Long id;
    private String name;
    private ResourceType resourceType;
    private ResourceStatus status;
    private Long hackathonId;
}
