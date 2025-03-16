package com.hacof.submission.service.impl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.hacof.submission.entity.User;
import com.hacof.submission.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hacof.submission.dto.request.RoundMarkCriterionRequestDTO;
import com.hacof.submission.dto.response.RoundMarkCriterionResponseDTO;
import com.hacof.submission.entity.RoundMarkCriterion;
import com.hacof.submission.entity.Round;
import com.hacof.submission.mapper.RoundMarkCriterionMapper;
import com.hacof.submission.repository.RoundMarkCriterionRepository;
import com.hacof.submission.repository.RoundRepository;
import com.hacof.submission.service.RoundMarkCriterionService;
import com.hacof.submission.util.SecurityUtil;

@Service
public class RoundMarkCriterionServiceImpl implements RoundMarkCriterionService {

    @Autowired
    private RoundMarkCriterionRepository repository;

    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoundMarkCriterionMapper mapper;

    @Override
    public List<RoundMarkCriterionResponseDTO> getAll() {
        List<RoundMarkCriterion> criterionList = repository.findAll();
        return criterionList.stream().map(mapper::toResponseDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<RoundMarkCriterionResponseDTO> getById(Long id) {
        Optional<RoundMarkCriterion> criterion = repository.findById(id);
        return criterion.map(mapper::toResponseDTO);
    }

    @Override
    public RoundMarkCriterionResponseDTO create(RoundMarkCriterionRequestDTO criterionDTO) {
        // Lấy Round từ database bằng roundId
        Optional<Round> roundOpt = roundRepository.findById(criterionDTO.getRoundId());
        if (!roundOpt.isPresent()) {
            throw new RuntimeException("Round not found with id " + criterionDTO.getRoundId());
        }

        // Chuyển DTO thành entity
        RoundMarkCriterion criterion = mapper.toEntity(criterionDTO);

        // Ensure that the roundId is set correctly in the entity
        Round round = roundOpt.get();  // Get the round entity
        criterion.setRound(round);  // Set the round in the criterion
        /// Lấy thông tin người dùng hiện tại từ SecurityUtil
        String currentUser = SecurityUtil.getCurrentUserLogin().orElse("anonymousUser");

        // Nếu là "anonymousUser", có thể bỏ qua hoặc xử lý đặc biệt
        if ("anonymousUser".equals(currentUser)) {
            // Tạo một giá trị mặc định cho "anonymousUser" hoặc xử lý đặc biệt
            currentUser = "admin";  // hoặc một user mặc định bạn muốn
        }
        final String finalCurrentUser = currentUser; // Assign it to a final variable

        // Tìm đối tượng User từ tên người dùng
        Optional<User> userOpt = userRepository.findByUsername(finalCurrentUser);
        User user = userOpt.orElseThrow(() -> new RuntimeException("User not found with username " + finalCurrentUser));

        // Chuyển đổi Instant thành LocalDateTime
        LocalDateTime now = LocalDateTime.now();

        // Gán các giá trị audit
        criterion.setCreatedDate(now);  // set thời gian tạo
        criterion.setCreatedBy(user);  // set đối tượng User vào createdBy
        criterion.setLastModifiedDate(now);  // set thời gian chỉnh sửa lần đầu

        // Lưu entity vào cơ sở dữ liệu
        RoundMarkCriterion savedCriterion = repository.save(criterion);

        // Chuyển đổi entity thành DTO và trả về
        return mapper.toResponseDTO(savedCriterion);
    }


    @Override
    public RoundMarkCriterionResponseDTO update(Long id, RoundMarkCriterionRequestDTO updatedCriterionDTO) {
        RoundMarkCriterion updated = repository
                .findById(id)
                .map(criterion -> {
                    // Lấy Round từ database bằng roundId
                    Optional<Round> roundOpt = roundRepository.findById(updatedCriterionDTO.getRoundId());
                    if (!roundOpt.isPresent()) {
                        throw new RuntimeException("Round not found with id " + updatedCriterionDTO.getRoundId());
                    }

                    // Lấy thông tin người dùng hiện tại

                    // Chuyển đổi Instant thành LocalDateTime
                    LocalDateTime now = LocalDateTime.now();

                    // Cập nhật thông tin trong entity
                    criterion.setName(updatedCriterionDTO.getName());
                    criterion.setNote(updatedCriterionDTO.getNote());
                    criterion.setMaxScore(updatedCriterionDTO.getMaxScore());
                    criterion.setRound(roundOpt.get());
                    criterion.setLastModifiedDate(now);  // Cập nhật thời gian chỉnh sửa

                    // Lưu lại entity đã cập nhật
                    return repository.save(criterion);
                })
                .orElseThrow(() -> new RuntimeException("RoundMarkCriterion not found with id " + id));

        // Chuyển entity đã cập nhật thành DTO và trả về
        return mapper.toResponseDTO(updated);
    }

    @Override
    public void delete(Long id) {
        Optional<RoundMarkCriterion> criterionOptional = repository.findById(id);
        if (criterionOptional.isPresent()) {
            RoundMarkCriterion criterion = criterionOptional.get();
            // Lưu lại entity với trường deletedAt đã được cập nhật
            repository.save(criterion);
        } else {
            throw new RuntimeException("Round mark criterion with id " + id + " not found");
        }
    }
}
