package com.hacof.hackathon.specification;

import com.hacof.hackathon.entity.Sponsorship;
import org.springframework.data.jpa.domain.Specification;

public class SponsorshipSpecification {
    public static Specification<Sponsorship> hasId(String id) {
        return (root, query, criteriaBuilder) -> id == null ? null : criteriaBuilder.equal(root.get("id"), id);
    }
}
