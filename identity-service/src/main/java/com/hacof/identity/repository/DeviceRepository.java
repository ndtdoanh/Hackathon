package com.hacof.identity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.identity.entity.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findByRoundId(Long roundId);

    List<Device> findByRoundLocationId(Long roundLocationId);
}
