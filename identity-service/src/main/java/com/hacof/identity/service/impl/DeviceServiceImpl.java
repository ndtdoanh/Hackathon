package com.hacof.identity.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hacof.identity.dto.request.DeviceRequest;
import com.hacof.identity.dto.response.DeviceResponse;
import com.hacof.identity.dto.response.FileUrlResponse;
import com.hacof.identity.entity.Device;
import com.hacof.identity.entity.FileUrl;
import com.hacof.identity.entity.Round;
import com.hacof.identity.entity.RoundLocation;
import com.hacof.identity.exception.AppException;
import com.hacof.identity.exception.ErrorCode;
import com.hacof.identity.mapper.DeviceMapper;
import com.hacof.identity.mapper.FileUrlMapper;
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
    FileUrlMapper fileUrlMapper;

    @Override
    public DeviceResponse createDevice(DeviceRequest request, List<MultipartFile> files) throws IOException {
        hackathonRepository
                .findById(Long.valueOf(request.getHackathonId()))
                .orElseThrow(() -> new AppException(ErrorCode.HACKATHON_NOT_FOUND));

        Round round = null;
        String roundIdStr = request.getRoundId();
        if (roundIdStr != null && !roundIdStr.trim().isEmpty()) {
            try {
                Long roundId = Long.valueOf(roundIdStr.trim());
                round = roundRepository
                        .findById(roundId)
                        .orElseThrow(() -> new AppException(ErrorCode.ROUND_NOT_FOUND));
            } catch (NumberFormatException e) {
                throw new AppException(ErrorCode.INVALID_INPUT);
            }
        }

        RoundLocation roundLocation = null;
        String roundLocationIdStr = request.getRoundLocationId();
        if (roundLocationIdStr != null && !roundLocationIdStr.trim().isEmpty()) {
            try {
                Long roundLocationId = Long.valueOf(roundLocationIdStr.trim());
                roundLocation = roundLocationRepository
                        .findById(roundLocationId)
                        .orElseThrow(() -> new AppException(ErrorCode.ROUND_LOCATION_NOT_FOUND));
            } catch (NumberFormatException e) {
                throw new AppException(ErrorCode.INVALID_INPUT);
            }
        }

        Device device = deviceMapper.toDevice(request);
        if (round != null) device.setRound(round);
        if (roundLocation != null) device.setRoundLocation(roundLocation);

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
            deviceRepository.save(savedDevice);
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
    public List<DeviceResponse> getDevicesByHackathonId(String hackathonId) {
        return deviceRepository.findByHackathonId(Long.valueOf(hackathonId)).stream()
                .map(deviceMapper::toDeviceResponse)
                .toList();
    }

    @Override
    public List<DeviceResponse> getDevicesByRoundId(String roundId) {
        return deviceRepository.findByRoundId(Long.valueOf(roundId)).stream()
                .map(deviceMapper::toDeviceResponse)
                .toList();
    }

    @Override
    public List<DeviceResponse> getDevicesByRoundLocationId(String roundLocationId) {
        return deviceRepository.findByRoundLocationId(Long.valueOf(roundLocationId)).stream()
                .map(deviceMapper::toDeviceResponse)
                .toList();
    }

    @Override
    public List<FileUrlResponse> getFileUrlsByDeviceId(Long deviceId) {
        Device device =
                deviceRepository.findById(deviceId).orElseThrow(() -> new AppException(ErrorCode.DEVICE_NOT_EXISTED));
        return fileUrlMapper.toResponseList(device.getFileUrls());
    }

    @Override
    public FileUrlResponse getFileUrlById(Long id) {
        return fileUrlRepository
                .findById(id)
                .map(fileUrlMapper::toResponse)
                .orElseThrow(() -> new AppException(ErrorCode.FILE_URL_NOT_EXISTED));
    }

    @Override
    public DeviceResponse updateDevice(Long id, DeviceRequest request, List<MultipartFile> files) throws IOException {
        Device existingDevice =
                deviceRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.DEVICE_NOT_FOUND));

        if (request.getName() != null) {
            existingDevice.setName(request.getName());
        }

        if (request.getDescription() != null) {
            existingDevice.setDescription(request.getDescription());
        }

        Integer quantity = request.getQuantity();
        if (quantity != null) {
            existingDevice.setQuantity(quantity);
        }

        if (request.getStatus() != null) {
            existingDevice.setStatus(request.getStatus());
        }

        String hackathonIdStr = request.getHackathonId();
        if (hackathonIdStr != null && !hackathonIdStr.trim().isEmpty()) {
            try {
                Long hackathonId = Long.valueOf(hackathonIdStr.trim());
                existingDevice.setHackathon(hackathonRepository
                        .findById(hackathonId)
                        .orElseThrow(() -> new AppException(ErrorCode.HACKATHON_NOT_FOUND)));
            } catch (NumberFormatException e) {
                throw new AppException(ErrorCode.INVALID_INPUT);
            }
        }

        String roundIdStr = request.getRoundId();
        if (roundIdStr != null) {
            if (!roundIdStr.trim().isEmpty()) {
                try {
                    Long roundId = Long.valueOf(roundIdStr.trim());
                    Round round = roundRepository
                            .findById(roundId)
                            .orElseThrow(() -> new AppException(ErrorCode.ROUND_NOT_FOUND));
                    existingDevice.setRound(round);
                } catch (NumberFormatException e) {
                    throw new AppException(ErrorCode.INVALID_INPUT);
                }
            } else {
                existingDevice.setRound(null);
            }
        }

        String roundLocationIdStr = request.getRoundLocationId();
        if (roundLocationIdStr != null) {
            if (!roundLocationIdStr.trim().isEmpty()) {
                try {
                    Long roundLocationId = Long.valueOf(roundLocationIdStr.trim());
                    RoundLocation roundLocation = roundLocationRepository
                            .findById(roundLocationId)
                            .orElseThrow(() -> new AppException(ErrorCode.ROUND_LOCATION_NOT_FOUND));
                    existingDevice.setRoundLocation(roundLocation);
                } catch (NumberFormatException e) {
                    throw new AppException(ErrorCode.INVALID_INPUT);
                }
            } else {
                existingDevice.setRoundLocation(null);
            }
        }

        Device updatedDevice = deviceRepository.save(existingDevice);

        if (files != null && !files.isEmpty()) {
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
                                updatedDevice);

                        fileUrlRepository.save(newFileUrl);
                    } catch (IOException e) {
                        throw new AppException(ErrorCode.FILE_UPLOAD_FAILED);
                    }
                }
            }

            updatedDevice =
                    deviceRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.DEVICE_NOT_FOUND));
        }

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
