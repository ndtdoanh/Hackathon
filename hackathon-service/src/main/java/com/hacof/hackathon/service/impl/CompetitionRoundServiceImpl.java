package com.hacof.hackathon.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hacof.hackathon.dto.CompetitionRoundDTO;
import com.hacof.hackathon.entity.CompetitionRound;
import com.hacof.hackathon.entity.Judge;
import com.hacof.hackathon.entity.Mentor;
import com.hacof.hackathon.entity.Team;
import com.hacof.hackathon.entity.User;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.CompetitionRoundMapper;
import com.hacof.hackathon.repository.CompetitionRoundRepository;
import com.hacof.hackathon.repository.HackathonRepository;
import com.hacof.hackathon.repository.JudgeRepository;
import com.hacof.hackathon.repository.MentorRepository;
import com.hacof.hackathon.repository.TeamRepository;
import com.hacof.hackathon.repository.UserRepository;
import com.hacof.hackathon.service.CompetitionRoundService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class CompetitionRoundServiceImpl implements CompetitionRoundService {
    final CompetitionRoundMapper roundMapper;
    final CompetitionRoundRepository roundRepository;
    final JudgeRepository judgeRepository;
    final TeamRepository teamRepository;
    final UserRepository userRepository;
    final CompetitionRoundRepository competitionRoundRepository;
    final MentorRepository mentorRepository;
    final HackathonRepository hackathonRepository;

    @Override
    public List<CompetitionRoundDTO> getAllRounds() {
        log.info("Fetching all competition rounds: ");
        if (competitionRoundRepository.findAll().isEmpty()) {
            throw new ResourceNotFoundException("No competition round found");
        }
        return roundRepository.findAll().stream().map(roundMapper::convertToDTO).toList();
    }

    @Override
    public CompetitionRoundDTO getRoundById(Long id) {
        log.info("Fetching competition round with id: {}", id);
        return roundRepository
                .findById(id)
                .map(roundMapper::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Round not found"));
    }

    @Override
    public CompetitionRoundDTO createRound(CompetitionRoundDTO roundDTO) {
        if (roundDTO.getHackathonId() == null || !hackathonRepository.existsById(roundDTO.getHackathonId())) {
            throw new ResourceNotFoundException("Hackathon not found with id: " + roundDTO.getHackathonId());
        }
        CompetitionRound round = roundMapper.convertToEntity(roundDTO);
        CompetitionRound savedRound = roundRepository.save(round);
        return roundMapper.convertToDTO(savedRound);
    }

    @Override
    public CompetitionRoundDTO updateRound(Long id, CompetitionRoundDTO roundDTO) {
        if (roundDTO.getHackathonId() == null || !hackathonRepository.existsById(roundDTO.getHackathonId())) {
            throw new ResourceNotFoundException("Hackathon not found with id: " + roundDTO.getHackathonId());
        }
        CompetitionRound existingRound =
                roundRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Round not found"));
        existingRound.setName(roundDTO.getName());
        existingRound.setDescription(roundDTO.getDescription());
        existingRound.setMaxTeam(roundDTO.getMaxTeam());
        CompetitionRound updatedRound = roundRepository.save(existingRound);
        return roundMapper.convertToDTO(updatedRound);
    }

    @Override
    public void deleteRound(Long id) {
        log.info("Deleting competition round with id: {}", id);
        CompetitionRound round =
                roundRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Round not found"));
        roundRepository.delete(round);
    }

    @Override
    public CompetitionRoundDTO assignJudgesAndMentors(Long roundId, List<Long> judgeIds, List<Long> mentorIds) {
        CompetitionRound round =
                roundRepository.findById(roundId).orElseThrow(() -> new ResourceNotFoundException("Round not found"));

        List<Judge> judges = judgeRepository.findAllById(judgeIds);
        List<Mentor> mentors = mentorRepository.findAllById(mentorIds);

        round.setJudges(judges);
        round.setMentors(mentors);

        CompetitionRound updatedRound = roundRepository.save(round);
        return roundMapper.convertToDTO(updatedRound);
    }

    @Override
    public void assignTaskToMember(Long teamId, Long memberId, String task) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new ResourceNotFoundException("Team not found"));
        User member =
                userRepository.findById(memberId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public List<String> getPassedTeams(Long roundId) {
        //        CompetitionRound round =
        //                roundRepository.findById(roundId).orElseThrow(() -> new ResourceNotFoundException("Round not
        // found"));
        //        return round.getTeams().stream()
        //                .filter(Team::isPassed)
        //                .map(Team::getName)
        //                .collect(Collectors.toList());
        return null;
    }

    @Override
    public List<String> getJudgeNames(Long roundId) {
        CompetitionRound round =
                roundRepository.findById(roundId).orElseThrow(() -> new ResourceNotFoundException("Round not found"));
        return round.getJudges().stream().map(Judge::getName).collect(Collectors.toList());
    }
}
