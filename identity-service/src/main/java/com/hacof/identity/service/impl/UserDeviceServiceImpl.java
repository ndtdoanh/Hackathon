package com.hacof.identity.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hacof.identity.dto.request.UserDeviceRequest;
import com.hacof.identity.dto.response.FileUrlResponse;
import com.hacof.identity.dto.response.UserDeviceResponse;
import com.hacof.identity.entity.FileUrl;
import com.hacof.identity.entity.UserDevice;
import com.hacof.identity.exception.AppException;
import com.hacof.identity.exception.ErrorCode;
import com.hacof.identity.mapper.FileUrlMapper;
import com.hacof.identity.mapper.UserDeviceMapper;
import com.hacof.identity.repository.DeviceRepository;
import com.hacof.identity.repository.FileUrlRepository;
import com.hacof.identity.repository.UserDeviceRepository;
import com.hacof.identity.repository.UserRepository;
import com.hacof.identity.service.UserDeviceService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserDeviceServiceImpl implements UserDeviceService {
    UserDeviceRepository userDeviceRepository;
    UserRepository userRepository;
    DeviceRepository deviceRepository;
    UserDeviceMapper userDeviceMapper;
    S3Service s3Service;
    FileUrlRepository fileUrlRepository;
    FileUrlMapper fileUrlMapper;

    @Override
    public UserDeviceResponse createUserDevice(UserDeviceRequest request, List<MultipartFile> files)
            throws IOException {

        userRepository
                .findById(Long.valueOf(request.getUserId()))
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        deviceRepository
                .findById(Long.valueOf(request.getDeviceId()))
                .orElseThrow(() -> new AppException(ErrorCode.DEVICE_NOT_FOUND));

        UserDevice userDevice = userDeviceMapper.toUserDevice(request);
        UserDevice savedUserDevice = userDeviceRepository.save(userDevice);

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
                                    savedUserDevice);
                        } catch (IOException e) {
                            throw new AppException(ErrorCode.FILE_UPLOAD_FAILED);
                        }
                    })
                    .collect(Collectors.toList());
            fileUrlRepository.saveAll(fileUrlList);
            savedUserDevice.setFileUrls(fileUrlList);
        }

        return userDeviceMapper.toUserDeviceResponse(savedUserDevice);
    }

    @Override
    public List<UserDeviceResponse> getUserDevices() {
        return userDeviceRepository.findAll().stream()
                .map(userDeviceMapper::toUserDeviceResponse)
                .toList();
    }

    @Override
    public UserDeviceResponse getUserDevice(Long id) {
        UserDevice userDevice = userDeviceRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_DEVICE_NOT_EXISTED));
        return userDeviceMapper.toUserDeviceResponse(userDevice);
    }

    @Override
    public List<UserDeviceResponse> getUserDevicesByDeviceId(String deviceId) {
        return userDeviceRepository.findByDeviceId(Long.valueOf(deviceId)).stream()
                .map(userDeviceMapper::toUserDeviceResponse)
                .toList();
    }

    @Override
    public List<UserDeviceResponse> getUserDevicesByUserId(String userId) {
        return userDeviceRepository.findByUserId(Long.valueOf(userId)).stream()
                .map(userDeviceMapper::toUserDeviceResponse)
                .toList();
    }

    @Override
    public List<FileUrlResponse> getFileUrlsByUserDeviceId(Long userDeviceId) {
        UserDevice userDevice = userDeviceRepository
                .findById(userDeviceId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_DEVICE_NOT_EXISTED));
        return fileUrlMapper.toResponseList(userDevice.getFileUrls());
    }

    @Override
    public UserDeviceResponse updateUserDevice(Long id, UserDeviceRequest request, List<MultipartFile> files)
            throws IOException {
        UserDevice existingUserDevice = userDeviceRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_DEVICE_NOT_EXISTED));

        userDeviceMapper.updateUserDeviceFromRequest(request, existingUserDevice);

        if (request.getUserId() != null) {
            existingUserDevice.setUser(userRepository
                    .findById(Long.valueOf(request.getUserId()))
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
        }

        if (request.getDeviceId() != null) {
            existingUserDevice.setDevice(deviceRepository
                    .findById(Long.valueOf(request.getDeviceId()))
                    .orElseThrow(() -> new AppException(ErrorCode.DEVICE_NOT_FOUND)));
        }

        if (files != null && !files.isEmpty()) {
            List<FileUrl> fileUrlList = new ArrayList<>();

            for (MultipartFile file : files) {
                if (file != null && !file.isEmpty() &&
                        file.getOriginalFilename() != null &&
                        !file.getOriginalFilename().trim().isEmpty()) {
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
                                existingUserDevice);

                        fileUrlList.add(newFileUrl);
                    } catch (IOException e) {
                        throw new AppException(ErrorCode.FILE_UPLOAD_FAILED);
                    }
                }
            }

            if (!fileUrlList.isEmpty()) {
                existingUserDevice.getFileUrls().addAll(fileUrlList);
                fileUrlRepository.saveAll(fileUrlList);
            }
        }

        UserDevice updatedUserDevice = userDeviceRepository.save(existingUserDevice);
        return userDeviceMapper.toUserDeviceResponse(updatedUserDevice);
    }

    @Override
    public void deleteUserDevice(Long id) {
        if (!userDeviceRepository.existsById(id)) {
            throw new AppException(ErrorCode.USER_DEVICE_NOT_EXISTED);
        }
        userDeviceRepository.deleteById(id);
    }
}
