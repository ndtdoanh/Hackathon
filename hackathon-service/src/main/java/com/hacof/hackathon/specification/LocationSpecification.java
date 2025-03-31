package com.hacof.hackathon.specification;

import org.springframework.data.jpa.domain.Specification;

import com.hacof.hackathon.entity.Location;

public class LocationSpecification {
    public static Specification<Location> hasId(String id) {
        return (root, query, cb) -> {
            if (id == null) return null;
            return cb.equal(root.get("id"), id);
        };
    }

    public static Specification<Location> hasName(String name) {
        return (root, query, cb) -> {
            if (name == null) return null;
            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<Location> hasAddress(String address) {
        return (root, query, cb) -> {
            if (address == null) return null;
            return cb.like(cb.lower(root.get("address")), "%" + address.toLowerCase() + "%");
        };
    }

    public static Specification<Location> hasLatitude(Double latitude) {
        return (root, query, cb) -> {
            if (latitude == null) return null;
            return cb.equal(root.get("latitude"), latitude);
        };
    }

    public static Specification<Location> hasLongitude(Double longitude) {
        return (root, query, cb) -> {
            if (longitude == null) return null;
            return cb.equal(root.get("longitude"), longitude);
        };
    }

    public static Specification<Location> hasLatitudeBetween(Double minLat, Double maxLat) {
        return (root, query, cb) -> {
            if (minLat == null || maxLat == null) return null;
            return cb.between(root.get("latitude"), minLat, maxLat);
        };
    }

    public static Specification<Location> hasLongitudeBetween(Double minLng, Double maxLng) {
        return (root, query, cb) -> {
            if (minLng == null || maxLng == null) return null;
            return cb.between(root.get("longitude"), minLng, maxLng);
        };
    }

    public static Specification<Location> hasCreatedBy(String createdBy) {
        return (root, query, criteriaBuilder) -> createdBy == null
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.equal(root.get("createdBy"), createdBy);
    }

    public static Specification<Location> hasLastModifiedBy(String lastModifiedBy) {
        return (root, query, criteriaBuilder) -> lastModifiedBy == null
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.equal(root.get("lastModifiedBy"), lastModifiedBy);
    }
}
