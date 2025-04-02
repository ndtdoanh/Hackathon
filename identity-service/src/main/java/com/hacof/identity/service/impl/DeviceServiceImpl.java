package com.hacof.identity.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hacof.identity.dto.request.DeviceRequest;
import com.hacof.identity.dto.response.DeviceResponse;
import com.hacof.identity.entity.Device;
import com.hacof.identity.entity.FileUrl;
import com.hacof.identity.exception.AppException;
import com.hacof.identity.exception.ErrorCode;
import com.hacof.identity.mapper.DeviceMapper;
import com.hacof.identity.repository.DeviceRepository;
import com.hacof.identity.repository.FileUrlRepository;
import com.hacof.identity.repository.HackathonRepository;
import com.hacof.identity.repository.RoundLocationRepository;
import com.hacof.identity.repository.RoundRepository;
import com.hacof.identity.service.DeviceService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DeviceServiceImpl implements DeviceService {

    DeviceRepository deviceRepository;
    DeviceMapper deviceMapper;
    HackathonRepository hackathonRepository;
    RoundRepository roundRepository;
    RoundLocationRepository roundLocationRepository;
    S3Service s3Service;
    FileUrlRepository fileUrlRepository;

    @Override
    public DeviceResponse createDevice(DeviceRequest request, List<MultipartFile> files) throws IOException {
        hackathonRepository
                .findById(Long.valueOf(request.getHackathonId()))
                .orElseThrow(() -> new AppException(ErrorCode.HACKATHON_NOT_FOUND));

        roundRepository
                .findById(Long.valueOf(request.getRoundId()))
                .orElseThrow(() -> new AppException(ErrorCode.ROUND_NOT_FOUND));

        roundLocationRepository
                .findById(Long.valueOf(request.getRoundLocationId()))
                .orElseThrow(() -> new AppException(ErrorCode.ROUND_LOCATION_NOT_FOUND));

        Device device = deviceMapper.toDevice(request);
        Device savedDevice = deviceRepository.save(device);

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
                                    savedDevice);
                        } catch (IOException e) {
                            throw new AppException(ErrorCode.FILE_UPLOAD_FAILED);
                        }
                    })
                    .collect(Collectors.toList());
            fileUrlRepository.saveAll(fileUrlList);
            savedDevice.setFileUrls(fileUrlList);
        }

        return deviceMapper.toDeviceResponse(savedDevice);
    }

    @Override
    public List<DeviceResponse> getDevices() {
        return deviceRepository.findAll().stream()
                .map(deviceMapper::toDeviceResponse)
                .toList();
    }

    @Override
    public DeviceResponse getDevice(Long id) {
        return deviceMapper.toDeviceResponse(
                deviceRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.DEVICE_NOT_EXISTED)));
    }

    @Override
    public DeviceResponse updateDevice(Long id, DeviceRequest request, List<MultipartFile> files) throws IOException {
        Device existingDevice =
                deviceRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.DEVICE_NOT_FOUND));

        deviceMapper.updateDeviceFromRequest(request, existingDevice);

        if (request.getHackathonId() != null) {
            existingDevice.setHackathon(hackathonRepository
                    .findById(Long.valueOf(request.getHackathonId()))
                    .orElseThrow(() -> new AppException(ErrorCode.HACKATHON_NOT_FOUND)));
        }
        if (request.getRoundId() != null) {
            existingDevice.setRound(roundRepository
                    .findById(Long.valueOf(request.getRoundId()))
                    .orElseThrow(() -> new AppException(ErrorCode.ROUND_NOT_FOUND)));
        }
        if (request.getRoundLocationId() != null) {
            existingDevice.setRoundLocation(roundLocationRepository
                    .findById(Long.valueOf(request.getRoundLocationId()))
                    .orElseThrow(() -> new AppException(ErrorCode.ROUND_LOCATION_NOT_FOUND)));
        }

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
                                    existingDevice);
                        } catch (IOException e) {
                            throw new AppException(ErrorCode.FILE_UPLOAD_FAILED);
                        }
                    })
                    .collect(Collectors.toList());

            fileUrlRepository.saveAll(fileUrlList);
            existingDevice.setFileUrls(fileUrlList);
        }

        Device updatedDevice = deviceRepository.save(existingDevice);
        return deviceMapper.toDeviceResponse(updatedDevice);
    }

    @Override
    public void deleteDevice(Long id) {
        if (!deviceRepository.existsById(id)) {
            throw new AppException(ErrorCode.DEVICE_NOT_EXISTED);
        }
        deviceRepository.deleteById(id);
    }
}
