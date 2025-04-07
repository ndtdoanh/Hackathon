package com.hacof.hackathon.configs;

import org.springframework.stereotype.Component;

import com.hacof.hackathon.entity.Location;

@Component
public class LocationMapperHelper {
    public Location fromId(String id) {
        if (id == null) return null;
        Location location = new Location();
        location.setId(Long.parseLong(id));
        return location;
    }
}
