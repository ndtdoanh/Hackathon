package com.hacof.hackathon.repository;

import com.hacof.hackathon.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {}
