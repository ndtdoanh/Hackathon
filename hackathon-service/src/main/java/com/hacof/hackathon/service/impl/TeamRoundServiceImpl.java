package com.hacof.hackathon.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hacof.hackathon.dto.TeamRoundDTO;
import com.hacof.hackathon.dto.TeamRoundSearchDTO;
import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.entity.Round;
import com.hacof.hackathon.entity.Team;
import com.hacof.hackathon.entity.TeamRound;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.TeamRoundMapper;
import com.hacof.hackathon.repository.RoundRepository;
import com.hacof.hackathon.repository.TeamRepository;
import com.hacof.hackathon.repository.TeamRoundRepository;
import com.hacof.hackathon.service.TeamRoundService;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TeamRoundServiceImpl implements TeamRoundService {
    private final TeamRoundRepository teamRoundRepository;
    private final TeamRepository teamRepository;
    private final RoundRepository roundRepository;
    private final TeamRoundMapper teamRoundMapper;

    @Override
    public TeamRoundDTO create(TeamRoundDTO teamRoundDTO) {
        log.debug("Tạo team round mới");

        // Validate team exists
        Team team = validateTeam(teamRoundDTO.getTeamId());

        // Validate round exists
        Round round = validateRound(teamRoundDTO.getRoundId());

        // Validate team belongs to hackathon
        validateTeamInHackathon(team, round.getHackathon());

        // Validate team not already in round
        validateTeamNotInRound(team.getId(), round.getId());

        // Create team round
        TeamRound teamRound = TeamRound.builder()
                .team(team)
                .round(round)
                .status(teamRoundDTO.getStatus())
                .description(teamRoundDTO.getDescription())
                .teamRoundJudges(new ArrayList<>())
                .build();

        return teamRoundMapper.toDto(teamRoundRepository.save(teamRound));
    }

    @Override
    public TeamRoundDTO update(String id, TeamRoundDTO teamRoundDTO) {
        TeamRound existingTeamRound = getTeamRound(id);

        //        // Validate team exists if changing
        //        if (!existingTeamRound.getTeam().getId().toString().equals(teamRoundDTO.getTeamId())) {
        //            Team newTeam = validateTeam(teamRoundDTO.getTeamId());
        //            existingTeamRound.setTeam(newTeam);
        //        }
        //
        //        // Validate round exists if changing
        //        if (!existingTeamRound.getRound().getId().toString().equals(teamRoundDTO.getRoundId())) {
        //            Round newRound = validateRound(teamRoundDTO.getRoundId());
        //            existingTeamRound.setRound(newRound);
        //        }

        existingTeamRound.setStatus(teamRoundDTO.getStatus());
        existingTeamRound.setDescription(teamRoundDTO.getDescription());

        return teamRoundMapper.toDto(teamRoundRepository.save(existingTeamRound));
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

        return teamRoundRepository.findAll(spec, pageRequest).map(teamRoundMapper::toDto);
    }

    private Team validateTeam(String teamId) {
        return teamRepository
                .findById(Long.parseLong(teamId))
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy team"));
    }

    private Round validateRound(String roundId) {
        return roundRepository
                .findById(Long.parseLong(roundId))
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy vòng thi"));
    }

    private void validateTeamInHackathon(Team team, Hackathon hackathon) {
        boolean exists = team.getTeamHackathons().stream()
                .anyMatch(th -> th.getHackathon().getId() == (hackathon.getId()));

        if (!exists) {
            throw new IllegalArgumentException("Team không thuộc hackathon này");
        }
    }

    private void validateTeamNotInRound(Long teamId, Long roundId) {
        if (teamRoundRepository.existsByTeamIdAndRoundId(teamId, roundId)) {
            throw new IllegalArgumentException("Team đã tồn tại trong vòng thi này");
        }
    }

    private TeamRound getTeamRound(String id) {
        return teamRoundRepository
                .findById(Long.parseLong(id))
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy team round"));
    }

    @Override
    public List<TeamRoundDTO> getAllByRoundId(String roundId) {
        List<TeamRound> teamRounds = teamRoundRepository.findAllByRoundId(Long.parseLong(roundId));
        return teamRounds.stream().map(teamRoundMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<TeamRoundDTO> getAllByJudgeIdAndRoundId(String judgeId, String roundId) {
        List<TeamRound> teamRounds =
                teamRoundRepository.findAllByJudgeIdAndRoundId(Long.parseLong(judgeId), Long.parseLong(roundId));
        return teamRounds.stream().map(teamRoundMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<TeamRoundDTO> updateBulk(List<TeamRoundDTO> teamRoundDTOs) {
        List<TeamRound> teamRounds = new ArrayList<>();
        for (TeamRoundDTO teamRoundDTO : teamRoundDTOs) {
            TeamRound existingTeamRound = teamRoundRepository.findById(Long.parseLong(teamRoundDTO.getId()))
                    .orElseThrow(() -> new ResourceNotFoundException("Not found team round with ID: " + teamRoundDTO.getId()));

            existingTeamRound.setStatus(teamRoundDTO.getStatus());
            existingTeamRound.setDescription(teamRoundDTO.getDescription());


            teamRounds.add(teamRoundRepository.save(existingTeamRound));
        }
        return teamRounds.stream().map(teamRoundMapper::toDto).collect(Collectors.toList());
    }

}
