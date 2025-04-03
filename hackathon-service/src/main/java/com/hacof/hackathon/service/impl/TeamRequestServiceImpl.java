package com.hacof.hackathon.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.hacof.hackathon.constant.Status;
import com.hacof.hackathon.constant.TeamHackathonStatus;
import com.hacof.hackathon.constant.TeamRequestMemberStatus;
import com.hacof.hackathon.constant.TeamRequestStatus;
import com.hacof.hackathon.dto.TeamRequestDTO;
import com.hacof.hackathon.dto.TeamRequestSearchDTO;
import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.entity.Team;
import com.hacof.hackathon.entity.TeamHackathon;
import com.hacof.hackathon.entity.TeamRequest;
import com.hacof.hackathon.entity.TeamRequestMember;
import com.hacof.hackathon.entity.User;
import com.hacof.hackathon.entity.UserTeam;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.TeamRequestMapper;
import com.hacof.hackathon.repository.HackathonRepository;
import com.hacof.hackathon.repository.TeamRepository;
import com.hacof.hackathon.repository.TeamRequestRepository;
import com.hacof.hackathon.repository.UserRepository;
import com.hacof.hackathon.service.NotificationService;
import com.hacof.hackathon.service.TeamRequestService;
import com.hacof.hackathon.specification.TeamRequestSpecification;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class TeamRequestServiceImpl implements TeamRequestService {
    TeamRequestRepository teamRequestRepository;
    TeamRepository teamRepository;
    HackathonRepository hackathonRepository;
    UserRepository userRepository;
    TeamRequestMapper teamRequestMapper;
    NotificationService notificationService;

    @Override
    public Page<TeamRequestDTO> searchTeamRequests(TeamRequestSearchDTO searchDTO) {
        Specification<TeamRequest> spec = Specification.where(null);

        if (searchDTO.getHackathonId() != null) {
            spec = spec.and(TeamRequestSpecification.hasHackathonId(searchDTO.getHackathonId()));
        }
        if (searchDTO.getTeamName() != null) {
            spec = spec.and(TeamRequestSpecification.hasTeamName(searchDTO.getTeamName()));
        }
        if (searchDTO.getStatus() != null) {
            spec = spec.and(TeamRequestSpecification.hasStatus(searchDTO.getStatus()));
        }
        if (searchDTO.getMemberId() != null) {
            spec = spec.and(TeamRequestSpecification.hasMemberId(searchDTO.getMemberId()));
        }
        if (searchDTO.getFromDate() != null || searchDTO.getToDate() != null) {
            spec = spec.and(
                    TeamRequestSpecification.createdDateBetween(searchDTO.getFromDate(), searchDTO.getToDate()));
        }

        // Pagination
        int page = searchDTO.getPage() != null ? searchDTO.getPage() : 0;
        int size = searchDTO.getSize() != null ? searchDTO.getSize() : 10;

        // Sorting
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        if (searchDTO.getSortBy() != null) {
            sort = Sort.by(
                    searchDTO.getSortDirection() != null
                                    && searchDTO.getSortDirection().equalsIgnoreCase("asc")
                            ? Sort.Direction.ASC
                            : Sort.Direction.DESC,
                    searchDTO.getSortBy());
        }

        PageRequest pageRequest = PageRequest.of(page, size, sort);

        return teamRequestRepository.findAll(spec, pageRequest).map(teamRequestMapper::toDto);
    }

    @Override
    public TeamRequestDTO createTeamRequest(TeamRequestDTO request) {
        log.info("Bắt đầu tạo team request cho hackathon: {}", request.getHackathonId());

        // Validate hackathon
        Hackathon hackathon = validateHackathon(request.getHackathonId());
        log.debug("Đã validate hackathon thành công");

        // Validate team size
        validateTeamSize(request.getTeamRequestMembers().size(), hackathon);
        log.debug("Đã validate team size thành công");

        // Validate members not in other teams
        //        validateMembersNotInOtherTeams(request.getTeamRequestMembers(), hackathon.getId());
        //        log.debug("Đã validate members không thuộc team khác thành công");

        // Create team request
        TeamRequest teamRequest = TeamRequest.builder()
                .hackathon(hackathon)
                .name(request.getName())
                .status(TeamRequestStatus.PENDING)
                .confirmationDeadline(LocalDateTime.now().plusDays(7))
                .note(request.getNote())
                .teamRequestMembers(new ArrayList<>())
                .build();

        // Add members
        request.getTeamRequestMembers().forEach(member -> {
            User user = userRepository
                    .findById(Long.parseLong(member.getUserId()))
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy user: " + member.getUserId()));

            TeamRequestMember memberEntity = TeamRequestMember.builder()
                    .teamRequest(teamRequest)
                    .user(user)
                    .status(TeamRequestMemberStatus.PENDING)
                    .build();
            teamRequest.getTeamRequestMembers().add(memberEntity);
        });

        TeamRequest saved = teamRequestRepository.save(teamRequest);
        log.info("Đã tạo team request thành công với ID: {}", saved.getId());

        notificationService.notifyTeamRequestCreated(saved);
        log.debug("Đã gửi thông báo cho các thành viên");

        return teamRequestMapper.toDto(saved);
    }

    @Override
    public TeamRequestDTO updateMemberResponse(
            String requestId, String userId, TeamRequestMemberStatus status, String note) {
        log.info("Update team request {} for request {}", userId, requestId);

        TeamRequest request = getTeamRequest(requestId);

        // Validate request still pending
        if (request.getStatus() != TeamRequestStatus.PENDING) {
            throw new IllegalStateException("Just can update response for pending request");
        }

        // Update member response
        TeamRequestMember member = request.getTeamRequestMembers().stream()
                .filter(m -> m.getUser().getId() == (Long.parseLong(userId)))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Not found member in team request"));

        member.setStatus(status);
        member.setNote(note);
        member.setRespondedAt(LocalDateTime.now());

        // Check if all members have responded
        boolean allResponded = request.getTeamRequestMembers().stream()
                .allMatch(m -> m.getStatus() != TeamRequestMemberStatus.PENDING);

        boolean allApproved = request.getTeamRequestMembers().stream()
                .allMatch(m -> m.getStatus() == TeamRequestMemberStatus.APPROVED);

        log.debug("Status respond - All responded: {}, All approved: {}", allResponded, allApproved);

        if (allResponded) {
            if (allApproved) {
                request.setStatus(TeamRequestStatus.UNDER_REVIEW);
                log.info("All members approved, change to UNDER_REVIEW");
            } else {
                request.setStatus(TeamRequestStatus.REJECTED);
                log.info("Have another member reject, change to REJECTED");
            }
        }

        TeamRequest updated = teamRequestRepository.save(request);
        notificationService.notifyMemberResponse(member);

        return teamRequestMapper.toDto(updated);
    }

    @Override
    public TeamRequestDTO reviewTeamRequest(String requestId, TeamRequestStatus status, String note) {
        log.info("Review team request: {}", requestId);

        TeamRequest request = getTeamRequest(requestId);

        //        // Validate request still pending
        //        if (request.getStatus() != TeamRequestStatus.PENDING) {
        //            throw new IllegalStateException("Chỉ có thể xét duyệt các yêu cầu đang chờ");
        //        }
        // Validate request is under review
        if (request.getStatus() != TeamRequestStatus.UNDER_REVIEW) {
            throw new IllegalStateException("Just can review request is waiting for approval");
        }

        // Validate all members approved if approving request
        if (status == TeamRequestStatus.APPROVED && !allMembersApproved(request)) {
            throw new IllegalStateException("Cannot approve request if not all members approved");
        }

        // Update request status
        request.setStatus(status);
        request.setNote(note);
        request.setReviewedAt(LocalDateTime.now());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user found");
        }

        String username = authentication.getName();
        User currentUser = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

        request.setReviewedBy(currentUser);

        // If approved, create team
        if (status == TeamRequestStatus.APPROVED) {
            createTeam(request);
            log.info(
                    "Created team from request                                                                                                                                      {}",
                    requestId);
        }

        TeamRequest updated = teamRequestRepository.save(request);
        notificationService.notifyTeamRequestReviewed(updated);

        return teamRequestMapper.toDto(updated);
    }

    private void createTeam(TeamRequest request) {
        Team team = Team.builder()
                .name(request.getName())
                .teamLeader(request.getCreatedBy()) // Set leader is the creator that send request
                .teamMembers(new HashSet<>())
                .teamHackathons(new ArrayList<>())
                .isDeleted(false)
                .build();

        // add new team
        TeamHackathon teamHackathon = TeamHackathon.builder()
                .team(team)
                .hackathon(request.getHackathon())
                .status(TeamHackathonStatus.ACTIVE)
                .build();
        team.getTeamHackathons().add(teamHackathon);

        // add members to team
        request.getTeamRequestMembers().forEach(member -> {
            UserTeam userTeam = UserTeam.builder()
                    .team(team)
                    .user(member.getUser())
                    .status(Status.ACTIVE)
                    .build();
            team.getTeamMembers().add(userTeam);
        });

        teamRepository.save(team);
    }

    private TeamRequest getTeamRequest(String requestId) {
        return teamRequestRepository
                .findById(Long.parseLong(requestId))
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy yêu cầu tạo team"));
    }

    private boolean allMembersApproved(TeamRequest request) {
        return request.getTeamRequestMembers().stream()
                .allMatch(member -> member.getStatus() == TeamRequestMemberStatus.APPROVED);
    }

    private void validateTeamSize(int size, Hackathon hackathon) {
        if (size < hackathon.getMinTeamSize() || size > hackathon.getMaxTeamSize()) {
            throw new IllegalArgumentException(String.format(
                    "Số lượng thành viên phải từ %d đến %d người",
                    hackathon.getMinTeamSize(), hackathon.getMaxTeamSize()));
        }
    }

    //    private void validateMembersNotInOtherTeams(List<TeamRequestMemberDTO> members, Long hackathonId) {
    //        for (TeamRequestMemberDTO member : members) {
    //            if (teamRepository.existsByHackathonIdAndMemberId(hackathonId, member.getUserId())) {
    //                throw new IllegalStateException(
    //                        String.format("User %s đã thuộc team khác trong hackathon này", member.getUserId()));
    //            }
    //        }
    //    }

    private Hackathon validateHackathon(String hackathonId) {
        Hackathon hackathon = hackathonRepository
                .findById(Long.parseLong(hackathonId))
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy hackathon"));

        if (hackathon.getStatus() != Status.ACTIVE) {
            throw new IllegalStateException("Hackathon không trong thời gian đăng ký");
        }

        return hackathon;
    }

    @Override
    public List<TeamRequestDTO> getAllByHackathonIdAndUserId(String hackathonId, String userId) {
        List<TeamRequest> teamRequests = teamRequestRepository.findAllByHackathonIdAndUserId(
                Long.parseLong(hackathonId), Long.parseLong(userId));
        return teamRequests.stream().map(teamRequestMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<TeamRequestDTO> filterByUserId(String userId) {
        List<TeamRequest> teamRequests = teamRequestRepository.findAllByUserId(Long.parseLong(userId));
        return teamRequests.stream().map(teamRequestMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<TeamRequestDTO> filterByHackathonId(String hackathonId) {
        List<TeamRequest> teamRequests = teamRequestRepository.findAllByHackathonId(Long.parseLong(hackathonId));
        return teamRequests.stream().map(teamRequestMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteTeamRequest(Long teamRequestId) {
        TeamRequest teamRequest = teamRequestRepository
                .findById(teamRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("TeamRequest not found with ID " + teamRequestId));
        teamRequestRepository.delete(teamRequest);
    }
}
