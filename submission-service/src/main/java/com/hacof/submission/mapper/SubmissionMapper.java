package com.hacof.submission.mapper;

import com.hacof.submission.dtos.request.SubmissionRequestDTO;
import com.hacof.submission.dtos.response.SubmissionResponseDTO;
import com.hacof.submission.entities.Submission;
import com.hacof.submission.entities.Team;
import com.hacof.submission.entities.Hackathon;
import com.hacof.submission.entities.Competitionround;
import com.hacof.submission.repositories.TeamRepository;
import com.hacof.submission.repositories.HackathonRepository;
import com.hacof.submission.repositories.CompetitionroundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubmissionMapper {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private HackathonRepository hackathonRepository;

    @Autowired
    private CompetitionroundRepository roundRepository;

    public Submission toEntity(SubmissionRequestDTO dto) {
        Submission submission = new Submission();

        // Lấy Team từ TeamRepository
        Team team = teamRepository.findById(dto.getTeamId())
                .orElseThrow(() -> new RuntimeException("Team not found"));
        submission.setTeam(team); // Gán đối tượng Team

        // Lấy Hackathon từ HackathonRepository
        Hackathon hackathon = hackathonRepository.findById(dto.getHackathonId())
                .orElseThrow(() -> new RuntimeException("Hackathon not found"));
        submission.setHackathon(hackathon); // Gán đối tượng Hackathon

        // Lấy Competitionround từ CompetitionroundRepository
        Competitionround round = roundRepository.findById(dto.getRoundId())
                .orElseThrow(() -> new RuntimeException("Round not found"));
        submission.setRound(round); // Gán đối tượng Round

        submission.setSubmissionUrl(dto.getSubmissionUrl());
        submission.setFeedback(dto.getFeedback());
        submission.setSubmittedAt(dto.getSubmittedAt());

        return submission;
    }

    public SubmissionResponseDTO toResponseDTO(Submission entity) {
        SubmissionResponseDTO dto = new SubmissionResponseDTO();

        dto.setId(entity.getId());
        dto.setTeamId(entity.getTeam().getId());  // Chỉ lấy ID của Team
        dto.setHackathonId(entity.getHackathon().getId());  // Chỉ lấy ID của Hackathon
        dto.setSubmissionUrl(entity.getSubmissionUrl());
        dto.setRoundId(entity.getRound().getId());  // Chỉ lấy ID của Round
        dto.setStatus(entity.getStatus().toString()); // Convert Status enum to String
        dto.setFeedback(entity.getFeedback());
        dto.setSubmittedAt(entity.getSubmittedAt());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setUpdatedBy(entity.getUpdatedBy());

        return dto;
    }

    public void updateEntityFromDTO(SubmissionRequestDTO dto, Submission entity) {
        // Gán lại các thuộc tính của Entity
        Team team = teamRepository.findById(dto.getTeamId())
                .orElseThrow(() -> new RuntimeException("Team not found"));
        entity.setTeam(team);  // Gán đối tượng Team

        Hackathon hackathon = hackathonRepository.findById(dto.getHackathonId())
                .orElseThrow(() -> new RuntimeException("Hackathon not found"));
        entity.setHackathon(hackathon);  // Gán đối tượng Hackathon

        Competitionround round = roundRepository.findById(dto.getRoundId())
                .orElseThrow(() -> new RuntimeException("Round not found"));
        entity.setRound(round);  // Gán đối tượng Round

        entity.setSubmissionUrl(dto.getSubmissionUrl());
        entity.setFeedback(dto.getFeedback());
        entity.setSubmittedAt(dto.getSubmittedAt());
    }
}
