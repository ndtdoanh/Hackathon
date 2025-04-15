package com.hacof.hackathon.specification;

import com.hacof.hackathon.entity.Hackathon;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HackathonSpecification {
    public static Specification<Hackathon> hasId(String id) {
        return (root, query, cb) -> {
            if (id == null) {
                return null;
            }
            return cb.equal(root.get("id"), id);
        };
    }

    public static Specification<Hackathon> hasTitle(String title) {
        return (root, query, cb) -> {
            if (StringUtils.isBlank(title)) {
                return null;
            }
            return cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
        };
    }

    public static Specification<Hackathon> hasSubTitle(String subTitle) {
        return (root, query, cb) -> {
            if (StringUtils.isBlank(subTitle)) {
                return null;
            }
            return cb.like(cb.lower(root.get("subTitle")), "%" + subTitle.toLowerCase() + "%");
        };
    }

    public static Specification<Hackathon> hasBannerImageUrl(String bannerImageUrl) {
        return (root, query, cb) -> {
            if (StringUtils.isBlank(bannerImageUrl)) {
                return null;
            }
            return cb.like(cb.lower(root.get("bannerImageUrl")), "%" + bannerImageUrl.toLowerCase() + "%");
        };
    }

    public static Specification<Hackathon> hasEnrollStartDate(LocalDateTime enrollStartDate) {
        return (root, query, cb) -> {
            if (enrollStartDate == null) {
                return null;
            }
            return cb.equal(root.get("enrollStartDate"), enrollStartDate);
        };
    }

    public static Specification<Hackathon> hasEnrollEndDate(LocalDateTime enrollEndDate) {
        return (root, query, cb) -> {
            if (enrollEndDate == null) {
                return null;
            }
            return cb.equal(root.get("enrollEndDate"), enrollEndDate);
        };
    }

    public static Specification<Hackathon> hasDescription(String description) {
        return (root, query, cb) -> {
            if (StringUtils.isBlank(description)) {
                return null;
            }
            return cb.like(cb.lower(root.get("description")), "%" + description.toLowerCase() + "%");
        };
    }

    public static Specification<Hackathon> hasInformation(String information) {
        return (root, query, cb) -> {
            if (StringUtils.isBlank(information)) {
                return null;
            }
            return cb.like(cb.lower(root.get("information")), "%" + information.toLowerCase() + "%");
        };
    }

    public static Specification<Hackathon> hasStartDate(LocalDateTime startDate) {
        return (root, query, cb) -> {
            if (startDate == null) {
                return null;
            }
            return cb.equal(root.get("startDate"), startDate);
        };
    }

    public static Specification<Hackathon> hasEndDate(LocalDateTime endDate) {
        return (root, query, cb) -> {
            if (endDate == null) {
                return null;
            }
            return cb.equal(root.get("endDate"), endDate);
        };
    }

    public static Specification<Hackathon> hasMaxTeams(Integer maxTeams) {
        return (root, query, cb) -> {
            if (maxTeams == null) {
                return null;
            }
            return cb.equal(root.get("maxTeams"), maxTeams);
        };
    }

    public static Specification<Hackathon> hasMinTeamSize(Integer minTeamSize) {
        return (root, query, cb) -> {
            if (minTeamSize == null) {
                return null;
            }
            return cb.equal(root.get("minTeamSize"), minTeamSize);
        };
    }

    public static Specification<Hackathon> hasMaxTeamSize(Integer maxTeamSize) {
        return (root, query, cb) -> {
            if (maxTeamSize == null) {
                return null;
            }
            return cb.equal(root.get("maxTeamSize"), maxTeamSize);
        };
    }

    public static Specification<Hackathon> hasEnrollmentCount(Integer enrollmentCount) {
        return (root, query, cb) -> {
            if (enrollmentCount == null) {
                return null;
            }
            return cb.equal(root.get("enrollmentCount"), enrollmentCount);
        };
    }

    public static Specification<Hackathon> hasMinimumTeamMembers(Integer minimumTeamMembers) {
        return (root, query, cb) -> {
            if (minimumTeamMembers == null) {
                return null;
            }
            return cb.equal(root.get("minimumTeamMembers"), minimumTeamMembers);
        };
    }

    public static Specification<Hackathon> hasMaximumTeamMembers(Integer maximumTeamMembers) {
        return (root, query, cb) -> {
            if (maximumTeamMembers == null) {
                return null;
            }
            return cb.equal(root.get("maximumTeamMembers"), maximumTeamMembers);
        };
    }

    public static Specification<Hackathon> hasEnrollmentStatus(String enrollmentStatus) {
        return (root, query, cb) -> {
            if (StringUtils.isBlank(enrollmentStatus)) {
                return null;
            }
            return cb.equal(root.get("enrollmentStatus"), enrollmentStatus);
        };
    }

    public static Specification<Hackathon> hasCategory(String category) {
        return (root, query, cb) -> {
            if (StringUtils.isBlank(category)) {
                return null;
            }
            return cb.equal(root.get("category"), category);
        };
    }

    public static Specification<Hackathon> hasStatus(String status) {
        return (root, query, cb) -> {
            if (StringUtils.isBlank(status)) {
                return null;
            }
            return cb.equal(root.get("status"), status);
        };
    }

    public static Specification<Hackathon> datesBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, cb) -> {
            if (startDate == null && endDate == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (startDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("startDate"), startDate));
            }
            if (endDate != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("endDate"), endDate));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Hackathon> teamSizeRange(Integer minTeamSize, Integer maxTeamSize) {
        return (root, query, cb) -> {
            if (minTeamSize == null && maxTeamSize == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (minTeamSize != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("minTeamSize"), minTeamSize));
            }
            if (maxTeamSize != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("maxTeamSize"), maxTeamSize));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Hackathon> byCategory(String category) {
        return (root, query, cb) -> {
            if (StringUtils.isBlank(category)) {
                return null;
            }
            return cb.equal(root.get("category"), category);
        };
    }

    public static Specification<Hackathon> byOrganization(String organization) {
        return (root, query, cb) -> {
            if (StringUtils.isBlank(organization)) {
                return null;
            }
            return cb.equal(root.get("organization"), organization);
        };
    }

    public static Specification<Hackathon> byContact(String contact) {
        return (root, query, cb) -> {
            if (StringUtils.isBlank(contact)) {
                return null;
            }
            return cb.equal(root.get("contact"), contact);
        };
    }
}
