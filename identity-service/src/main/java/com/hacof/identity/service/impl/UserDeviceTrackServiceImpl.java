package com.hacof.identity.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hacof.identity.dto.request.UserDeviceTrackRequest;
import com.hacof.identity.dto.response.FileUrlResponse;
import com.hacof.identity.dto.response.UserDeviceTrackResponse;
import com.hacof.identity.entity.FileUrl;
import com.hacof.identity.entity.UserDeviceTrack;
import com.hacof.identity.exception.AppException;
import com.hacof.identity.exception.ErrorCode;
import com.hacof.identity.mapper.FileUrlMapper;
import com.hacof.identity.mapper.UserDeviceTrackMapper;
import com.hacof.identity.repository.FileUrlRepository;
import com.hacof.identity.repository.UserDeviceRepository;
import com.hacof.identity.repository.UserDeviceTrackRepository;
import com.hacof.identity.service.UserDeviceTrackService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserDeviceTrackServiceImpl implements UserDeviceTrackService {
    UserDeviceTrackRepository userDeviceTrackRepository;
    UserDeviceRepository userDeviceRepository;
    UserDeviceTrackMapper userDeviceTrackMapper;
    S3Service s3Service;
    FileUrlRepository fileUrlRepository;
    FileUrlMapper fileUrlMapper;

    @Override
    public UserDeviceTrackResponse createUserDeviceTrack(UserDeviceTrackRequest request, List<MultipartFile> files)
            throws Exception {
        userDeviceRepository
                .findById(Long.valueOf(request.getUserDeviceId()))
                .orElseThrow(() -> new AppException(ErrorCode.USER_DEVICE_NOT_EXISTED));

        UserDeviceTrack userDeviceTrack = userDeviceTrackMapper.toUserDeviceTrack(request);
        UserDeviceTrack savedUserDeviceTrack = userDeviceTrackRepository.save(userDeviceTrack);

        if (files != null && !files.isEmpty()) {
            List<FileUrl> fileUrlList = files.stream()
                    .map(file -> {
                        try {
                            String fileUrl = s3Service.uploadFile(
                                    file.getInputStream(),
                                    file.getOriginalFilename(),
                                    file.getSize(),
                                    file.getContentType());

                            return new FileUrl(
                                    file.getOriginalFilename(),
                                    fileUrl,
                                    file.getContentType(),
                                    (int) file.getSize(),
                                    savedUserDeviceTrack);
                        } catch (IOException e) {
                            throw new AppException(ErrorCode.FILE_UPLOAD_FAILED);
                        }
                    })
                    .collect(Collectors.toList());
            fileUrlRepository.saveAll(fileUrlList);
            savedUserDeviceTrack.setFileUrls(fileUrlList);
        }

        return userDeviceTrackMapper.toUserDeviceTrackResponse(savedUserDeviceTrack);
    }

    @Override
    public List<UserDeviceTrackResponse> getUserDeviceTracks() {
        return userDeviceTrackRepository.findAll().stream()
                .map(userDeviceTrackMapper::toUserDeviceTrackResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserDeviceTrackResponse getUserDeviceTrack(Long id) {
        return userDeviceTrackRepository
                .findById(id)
                .map(userDeviceTrackMapper::toUserDeviceTrackResponse)
                .orElseThrow(() -> new AppException(ErrorCode.USER_DEVICE_TRACK_NOT_EXISTED));
    }

    @Override
    public List<UserDeviceTrackResponse> getUserDeviceTracksByUserDeviceId(Long userDeviceId) {
        return userDeviceTrackRepository.findByUserDeviceId(userDeviceId).stream()
                .map(userDeviceTrackMapper::toUserDeviceTrackResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<FileUrlResponse> getFileUrlsByUserDeviceTrackId(Long userDeviceTrackId) {
        UserDeviceTrack userDeviceTrack = userDeviceTrackRepository
                .findById(userDeviceTrackId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_DEVICE_TRACK_NOT_EXISTED));
        return fileUrlMapper.toResponseList(userDeviceTrack.getFileUrls());
    }

    @Override
    public UserDeviceTrackResponse updateUserDeviceTrack(
            Long id, UserDeviceTrackRequest request, List<MultipartFile> files) throws Exception {
        UserDeviceTrack existingUserDeviceTrack = userDeviceTrackRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_DEVICE_TRACK_NOT_EXISTED));

        userDeviceTrackMapper.updateUserDeviceTrack(request, existingUserDeviceTrack);

        if (request.getUserDeviceId() != null) {
            existingUserDeviceTrack.setUserDevice(userDeviceRepository
                    .findById(Long.valueOf(request.getUserDeviceId()))
                    .orElseThrow(() -> new AppException(ErrorCode.USER_DEVICE_NOT_EXISTED)));
        }

        if (files != null && !files.isEmpty()) {
            List<FileUrl> fileUrlList = new ArrayList<>();

            for (MultipartFile file : files) {
                if (file != null
                        && !file.isEmpty()
                        && file.getOriginalFilename() != null
                        && !file.getOriginalFilename().trim().isEmpty()) {
                    try {
                        String fileUrl = s3Service.uploadFile(
                                file.getInputStream(),
                                file.getOriginalFilename(),
                                file.getSize(),
                                file.getContentType());

                        FileUrl newFileUrl = new FileUrl(
                                file.getOriginalFilename(),
                                fileUrl,
                                file.getContentType(),
                                (int) file.getSize(),
                                existingUserDeviceTrack);

                        fileUrlList.add(newFileUrl);
                    } catch (IOException e) {
                        throw new AppException(ErrorCode.FILE_UPLOAD_FAILED);
                    }
                }
            }

            if (!fileUrlList.isEmpty()) {
                existingUserDeviceTrack.getFileUrls().addAll(fileUrlList);
                fileUrlRepository.saveAll(fileUrlList);
            }
        }

        UserDeviceTrack updatedUserDeviceTrack = userDeviceTrackRepository.save(existingUserDeviceTrack);
        return userDeviceTrackMapper.toUserDeviceTrackResponse(updatedUserDeviceTrack);
    }

    @Override
    public void deleteUserDeviceTrack(Long id) {
        userDeviceTrackRepository.deleteById(id);
    }
}
