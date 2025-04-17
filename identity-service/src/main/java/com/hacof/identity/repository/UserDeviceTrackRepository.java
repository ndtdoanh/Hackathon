package com.hacof.identity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.identity.entity.UserDeviceTrack;

@Repository
public interface UserDeviceTrackRepository extends JpaRepository<UserDeviceTrack, Long> {
    List<UserDeviceTrack> findByUserDeviceId(Long userDeviceId);
}
