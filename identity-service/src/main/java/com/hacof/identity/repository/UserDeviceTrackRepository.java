package com.hacof.identity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.identity.entity.UserDeviceTrack;

@Repository
public interface UserDeviceTrackRepository extends JpaRepository<UserDeviceTrack, Long> {}
