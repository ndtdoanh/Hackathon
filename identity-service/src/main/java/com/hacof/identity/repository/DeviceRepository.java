package com.hacof.identity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.identity.entity.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    boolean existsByName(String name);
}
