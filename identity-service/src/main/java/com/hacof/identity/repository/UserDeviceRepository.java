package com.hacof.identity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.identity.entity.UserDevice;

@Repository
public interface UserDeviceRepository extends JpaRepository<UserDevice, Long> {
    List<UserDevice> findByDeviceId(Long deviceId);

    List<UserDevice> findByUserId(Long userId);
}
