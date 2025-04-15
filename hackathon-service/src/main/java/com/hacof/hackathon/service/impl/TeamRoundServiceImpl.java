package com.hacof.hackathon.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hacof.hackathon.constant.TeamRoundStatus;
import com.hacof.hackathon.dto.TeamRoundDTO;
import com.hacof.hackathon.dto.TeamRoundSearchDTO;
import com.hacof.hackathon.entity.*;
import com.hacof.hackathon.exception.InvalidInputException;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.manual.TeamMapperManual;
import com.hacof.hackathon.mapper.manual.TeamRoundMapperManual;
import com.hacof.hackathon.repository.RoundRepository;
import com.hacof.hackathon.repository.TeamRepository;
import com.hacof.hackathon.repository.TeamRoundRepository;
import com.hacof.hackathon.service.TeamRoundService;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
@FieldDefaults(makeFinal = true)
public class TeamRoundServiceImpl implements TeamRoundService {
    TeamRoundRepository teamRoundRepository;
    TeamRepository teamRepository;
    RoundRepository roundRepository;

    @Override
    public TeamRoundDTO create(TeamRoundDTO teamRoundDTO) {
        Team team = validateTeam(teamRoundDTO.getTeamId());

        Round round = validateRound(teamRoundDTO.getRoundId());

        validateTeamInHackathon(team, round.getHackathon());

        validateTeamNotInRound(team.getId(), round.getId());

        String currentUser =
                teamRoundDTO.getCreatedByUserName() != null ? teamRoundDTO.getCreatedByUserName() : "admin";
        LocalDateTime now = LocalDateTime.now();

        // Create team round
        TeamRound teamRound = TeamRound.builder()
                .team(team)
                .round(round)
                .status(teamRoundDTO.getStatus())
                .description(teamRoundDTO.getDescription())
                .teamRoundJudges(new ArrayList<>())
                .build();

        return TeamRoundMapperManual.toDto(teamRoundRepository.save(teamRound));
    }

    @Override
    public TeamRoundDTO update(String id, TeamRoundDTO dto) {
        if (dto == null) {
            throw new InvalidInputException("Request body cannot be null");
        }

        Long teamRoundId = parseId(id, "TeamRound");
        Long teamId = parseId(dto.getTeamId(), "Team");
        Long roundId = parseId(dto.getRoundId(), "Round");

        TeamRound existing = teamRoundRepository
                .findById(Long.parseLong(id))
                .orElseThrow(() -> new ResourceNotFoundException("TeamRound not found"));

        Team team = teamRepository
                .findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found with ID: " + teamId));

        Round round = roundRepository
                .findById(roundId)
                .orElseThrow(() -> new ResourceNotFoundException("Round not found with ID: " + roundId));

        boolean alreadyInRound = teamRoundRepository.existsByTeamIdAndRoundIdAndIdNot(teamId, roundId, teamRoundId);

        // Check if the team is already in this round (excluding the current one)

        if (alreadyInRound) {
            throw new InvalidInputException("Team is already registered in this round");
        }

        existing.setStatus(dto.getStatus());
        existing.setDescription(dto.getDescription());
        teamRoundRepository.save(existing);

        if (dto.getStatus() == TeamRoundStatus.PASSED) {
            createNextRoundForTeam(existing);
        }

        return TeamRoundMapperManual.toDto(existing);
    }

    private void createNextRoundForTeam(TeamRound passedRound) {
        Round currentRound = passedRound.getRound();
        int nextRoundNumber = currentRound.getRoundNumber() + 1;
        Long hackathonId = currentRound.getHackathon().getId();

        Optional<Round> nextRoundOpt = roundRepository.findByHackathonIdAndRoundNumber(hackathonId, nextRoundNumber);

        if (nextRoundOpt.isPresent()) {
            Round nextRound = nextRoundOpt.get();

            boolean exists = teamRoundRepository.existsByTeamIdAndRoundId(
                    passedRound.getTeam().getId(), nextRound.getId());

            if (!exists) {
                TeamRound nextTeamRound = new TeamRound();
                nextTeamRound.setTeam(passedRound.getTeam());
                nextTeamRound.setRound(nextRound);
                nextTeamRound.setStatus(TeamRoundStatus.PENDING);
                nextTeamRound.setDescription("Auto-generated for next round");

                teamRoundRepository.save(nextTeamRound);
            }
        }
    }

    @Override
    public void delete(String id) {
        if (!teamRoundRepository.existsById(Long.parseLong(id))) {
            throw new ResourceNotFoundException("Không tìm thấy team round");
        }
        teamRoundRepository.deleteById(Long.parseLong(id));
    }

    @Override
    public Page<TeamRoundDTO> searchTeamRounds(TeamRoundSearchDTO searchDTO) {
        Specification<TeamRound> spec = Specification.where(null);

        if (StringUtils.isNotBlank(searchDTO.getTeamId())) {
            spec = spec.and(
                    (root, query, cb) -> cb.equal(root.get("team").get("id"), Long.parseLong(searchDTO.getTeamId())));
        }

        if (StringUtils.isNotBlank(searchDTO.getRoundId())) {
            spec = spec.and(
                    (root, query, cb) -> cb.equal(root.get("round").get("id"), Long.parseLong(searchDTO.getRoundId())));
        }

        if (searchDTO.getStatus() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), searchDTO.getStatus()));
        }

        Sort sort = Sort.by(
                searchDTO.getSortDirection().equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC,
                searchDTO.getSortBy());

        PageRequest pageRequest = PageRequest.of(searchDTO.getPage(), searchDTO.getSize(), sort);

        return teamRoundRepository.findAll(spec, pageRequest).map(TeamRoundMapperManual::toDto);
    }

    private Team validateTeam(String teamId) {
        return teamRepository
                .findById(Long.parseLong(teamId))
                .orElseThrow(() -> new ResourceNotFoundException("Team was not found"));
    }

    private Round validateRound(String roundId) {
        return roundRepository
                .findById(Long.parseLong(roundId))
                .orElseThrow(() -> new ResourceNotFoundException("Round was not found"));
    }

    private void validateTeamInHackathon(Team team, Hackathon hackathon) {
        boolean exists = team.getTeamHackathons().stream()
                .anyMatch(th -> th.getHackathon().getId() == (hackathon.getId()));

        if (!exists) {
            throw new InvalidInputException("Team was not belong to hackathon");
        }
    }

    private void validateTeamNotInRound(Long teamId, Long roundId) {
        if (teamRoundRepository.existsByTeamIdAndRoundId(teamId, roundId)) {
            throw new InvalidInputException("Team was already in round");
        }
    }

    private TeamRound getTeamRound(String id) {
        return teamRoundRepository
                .findById(Long.parseLong(id))
                .orElseThrow(() -> new ResourceNotFoundException("Team round was not found"));
    }

    @Override
    public List<TeamRoundDTO> getAllByRoundId(String roundId) {
        List<TeamRound> teamRounds = teamRoundRepository.findAllByRoundId(Long.parseLong(roundId));

        return teamRounds.stream()
                .map(tr -> {
                    TeamRoundDTO dto = TeamRoundMapperManual.toDto(tr);
                    dto.setTeam(TeamMapperManual.toDtoWithLeaderAndMembers(tr.getTeam()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<TeamRoundDTO> getAllByJudgeIdAndRoundId(String judgeId, String roundId) {
        Long jId = Long.parseLong(judgeId);
        Long rId = Long.parseLong(roundId);

        List<TeamRound> teamRounds = teamRoundRepository.findAllByRoundIdAndJudgeId(rId, jId);

        return teamRounds.stream()
                .map(tr -> {
                    TeamRoundDTO dto = TeamRoundMapperManual.toDto(tr);
                    dto.setTeam(TeamMapperManual.toDtoWithLeaderAndMembers(tr.getTeam()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<TeamRoundDTO> updateBulk(List<TeamRoundDTO> teamRoundDTOs) {
        List<TeamRound> teamRounds = new ArrayList<>();
        for (TeamRoundDTO teamRoundDTO : teamRoundDTOs) {
            TeamRound existingTeamRound = teamRoundRepository
                    .findById(Long.parseLong(teamRoundDTO.getId()))
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Not found team round with ID: " + teamRoundDTO.getId()));

            existingTeamRound.setStatus(teamRoundDTO.getStatus());
            existingTeamRound.setDescription(teamRoundDTO.getDescription());

            teamRounds.add(teamRoundRepository.save(existingTeamRound));
        }
        return teamRounds.stream().map(TeamRoundMapperManual::toDto).collect(Collectors.toList());
    }

    private Long parseId(String idStr, String fieldName) {
        try {
            return Long.parseLong(idStr);
        } catch (NumberFormatException e) {
            throw new InvalidInputException(fieldName + " ID is invalid");
        }
    }
}
