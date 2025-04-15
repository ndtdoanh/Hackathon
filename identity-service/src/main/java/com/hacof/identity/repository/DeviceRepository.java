package com.hacof.identity.repository;

import com.hacof.identity.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    List<Device> findByHackathonId(Long hackathonId);

    List<Device> findByRoundId(Long roundId);

    List<Device> findByRoundLocationId(Long roundLocationId);
}
