package com.hacof.identity.repository;

import com.hacof.identity.entity.UserDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDeviceRepository extends JpaRepository<UserDevice, Long> {
    List<UserDevice> findByDeviceId(Long deviceId);

    List<UserDevice> findByUserId(Long userId);
}
