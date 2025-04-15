package com.hacof.identity.repository;

import com.hacof.identity.entity.UserDeviceTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDeviceTrackRepository extends JpaRepository<UserDeviceTrack, Long> {
    List<UserDeviceTrack> findByUserDeviceId(Long userDeviceId);
}
