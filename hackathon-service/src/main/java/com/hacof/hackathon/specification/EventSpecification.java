package com.hacof.hackathon.specification;

import com.hacof.hackathon.entity.Event;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class EventSpecification {
    public static Specification<Event> hasId(Long id) {
        return (root, query, criteriaBuilder) -> id == null ? null : criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<Event> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                name == null ? null : criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Event> hasDescription(String description) {
        return (root, query, criteriaBuilder) ->
                description == null ? null : criteriaBuilder.like(root.get("description"), "%" + description + "%");
    }

    public static Specification<Event> hasEventDate(LocalDateTime eventDate) {
        return (root, query, criteriaBuilder) ->
                eventDate == null ? null : criteriaBuilder.equal(root.get("eventDate"), eventDate);
    }

    public static Specification<Event> hasNotificationSent(Boolean notificationSent) {
        return (root, query, criteriaBuilder) ->
                notificationSent == null ? null : criteriaBuilder.equal(root.get("notificationSent"), notificationSent);
    }

    public static Specification<Event> hasEventType(String eventType) {
        return (root, query, criteriaBuilder) ->
                eventType == null ? null : criteriaBuilder.equal(root.get("eventType"), eventType);
    }

    public static Specification<Event> hasHackathonId(Long hackathonId) {
        return (root, query, criteriaBuilder) -> hackathonId == null
                ? null
                : criteriaBuilder.equal(root.get("hackathon").get("id"), hackathonId);
    }

    public static Specification<Event> hasOrganizerId(Long organizerId) {
        return (root, query, criteriaBuilder) -> organizerId == null
                ? null
                : criteriaBuilder.equal(root.get("organizer").get("id"), organizerId);
    }
}
