package com.hacof.hackathon.repository;

import com.hacof.hackathon.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {
    List<Event> findByHackathonId(Long hackathonId);

    List<Event> findByOrganizerId(Long organizerId);
}
