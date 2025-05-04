package com.hacof.hackathon.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.hacof.hackathon.constant.TeamRequestMemberStatus;
import com.hacof.hackathon.constant.TeamRequestStatus;
import com.hacof.hackathon.entity.TeamRequest;
import com.hacof.hackathon.entity.TeamRequestMember;
import com.hacof.hackathon.entity.User;
import com.hacof.hackathon.exception.InvalidInputException;
import com.hacof.hackathon.service.EmailService;
import com.hacof.hackathon.service.NotificationService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true)
public class NotificationServiceImpl implements NotificationService {
    EmailService emailService;

    @Override
    public void notifyTeamRequestCreated(TeamRequest request) {
        validateTeamRequest(request);

        String emailContent = String.format(
                """
				Dear Team Member,

				You have been invited to join team "%s" in hackathon "%s".

				Please respond to this invitation within %d days.

				Best regards,
				Hackathon Team
				""",
                request.getName(), request.getHackathon().getTitle(), 7);

        List<String> memberEmails = extractMemberEmails(request);

        if (!memberEmails.isEmpty()) {
            try {
                emailService.sendBulkEmails(memberEmails, "Team Invitation", emailContent);
                log.info("Sent team invitation to {} members", memberEmails.size());
            } catch (Exception e) {
                log.error("Failed to send team invitation emails", e);
            }
        }
    }

    @Override
    public void notifyMemberResponse(TeamRequestMember member) {
        validateMemberResponse(member);

        String emailContent = String.format(
                """
				Dear Team Leader,

				Member %s has %s your team invitation for team "%s" in hackathon "%s".

				Current status of your team request:
				- Total invited: %d
				- Approved: %d
				- Pending: %d
				- Rejected: %d

				Best regards,
				Hackathon Team
				""",
                member.getUser().getUsername(),
                member.getStatus() == TeamRequestMemberStatus.APPROVED ? "APPROVED" : "REJECTED",
                member.getTeamRequest().getName(),
                member.getTeamRequest().getHackathon().getTitle(),
                member.getTeamRequest().getTeamRequestMembers().size(),
                countMembersByStatus(member.getTeamRequest(), TeamRequestMemberStatus.APPROVED),
                countMembersByStatus(member.getTeamRequest(), TeamRequestMemberStatus.PENDING),
                countMembersByStatus(member.getTeamRequest(), TeamRequestMemberStatus.REJECTED));

        String creatorEmail = extractCreatorEmail(member.getTeamRequest());

        if (creatorEmail != null) {
            try {
                emailService.sendEmail(creatorEmail, "Team Invitation Response", emailContent);
                log.debug("Sent member response notification to creator: {}", creatorEmail);
            } catch (Exception e) {
                log.error("Failed to send member response notification", e);
            }
        }
    }

    @Override
    public void notifyTeamRequestReviewed(TeamRequest request) {
        validateTeamRequest(request);

        String statusAndReason = generateStatusAndReason(request);

        String emailContent = String.format(
                """
				Dear Team Members,

				Your team request "%s" for hackathon "%s" has been %s

				Next steps:
				- Approved teams: Prepare for the hackathon!
				- Rejected teams: You may submit another request if allowed.

				Best regards,
				Hackathon Organizers
				""",
                request.getName(), request.getHackathon().getTitle(), statusAndReason);
        List<String> allRecipients = extractAllRecipientEmails(request);
        if (!allRecipients.isEmpty()) {
            try {
                emailService.sendBulkEmails(allRecipients, "Team Request Status Update", emailContent);
                log.info("Sent team status update to {} recipients", allRecipients.size());
            } catch (Exception e) {
                log.error("Failed to send team status update", e);
            }
        }
    }

    // Helper methods
    private List<String> extractMemberEmails(TeamRequest request) {
        return request.getTeamRequestMembers().stream()
                .map(TeamRequestMember::getUser)
                .filter(Objects::nonNull)
                .map(User::getEmail)
                .filter(email -> email != null && !email.trim().isEmpty())
                .distinct()
                .collect(Collectors.toList());
    }

    private String extractCreatorEmail(TeamRequest request) {
        return Optional.ofNullable(request.getCreatedBy())
                .map(User::getEmail)
                .filter(email -> !email.trim().isEmpty())
                .orElse(null);
    }

    private List<String> extractAllRecipientEmails(TeamRequest request) {
        return Stream.concat(
                        Stream.ofNullable(request.getCreatedBy())
                                .map(User::getEmail)
                                .filter(email -> email != null && !email.trim().isEmpty()),
                        request.getTeamRequestMembers().stream()
                                .map(TeamRequestMember::getUser)
                                .filter(Objects::nonNull)
                                .map(User::getEmail)
                                .filter(email -> email != null && !email.trim().isEmpty()))
                .distinct()
                .collect(Collectors.toList());
    }

    private long countMembersByStatus(TeamRequest request, TeamRequestMemberStatus status) {
        return request.getTeamRequestMembers().stream()
                .filter(member -> member.getStatus() == status)
                .count();
    }

    private void validateTeamRequest(TeamRequest request) {
        if (request == null) {
            throw new InvalidInputException("TeamRequest cannot be null");
        }
        if (request.getHackathon() == null) {
            throw new InvalidInputException("Hackathon reference cannot be null");
        }
    }

    private void validateMemberResponse(TeamRequestMember member) {
        if (member == null) {
            throw new InvalidInputException("TeamRequestMember cannot be null");
        }
        if (member.getUser() == null) {
            throw new InvalidInputException("User cannot be null");
        }
        if (member.getTeamRequest() == null) {
            throw new InvalidInputException("TeamRequest cannot be null");
        }
    }

    private String generateStatusAndReason(TeamRequest request) {
        String status = request.getStatus() == TeamRequestStatus.APPROVED ? "approved" : "rejected";
        String reason = request.getStatus() == TeamRequestStatus.REJECTED ? "\n\nReason: " + request.getNote() : "";
        return String.format("%s%s", status, reason);
    }
}
