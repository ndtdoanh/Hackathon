package com.hacof.identity.service;

import java.util.List;

import com.hacof.identity.dto.response.FileUrlResponse;
import org.springframework.web.multipart.MultipartFile;

import com.hacof.identity.dto.request.UserDeviceTrackRequest;
import com.hacof.identity.dto.response.UserDeviceTrackResponse;

public interface UserDeviceTrackService {

    UserDeviceTrackResponse createUserDeviceTrack(UserDeviceTrackRequest request, List<MultipartFile> files)
            throws Exception;

    List<UserDeviceTrackResponse> getUserDeviceTracks();

    UserDeviceTrackResponse getUserDeviceTrack(Long id);

    List<UserDeviceTrackResponse> getUserDeviceTracksByUserDeviceId(Long userDeviceId);

    List<FileUrlResponse> getFileUrlsByUserDeviceTrackId(Long userDeviceTrackId);

    UserDeviceTrackResponse updateUserDeviceTrack(Long id, UserDeviceTrackRequest request, List<MultipartFile> files)
            throws Exception;

    void deleteUserDeviceTrack(Long id);
}
