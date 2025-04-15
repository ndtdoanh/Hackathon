package com.hacof.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.hackathon.entity.Device;

public interface DeviceRepository extends JpaRepository<Device, Long> {}
