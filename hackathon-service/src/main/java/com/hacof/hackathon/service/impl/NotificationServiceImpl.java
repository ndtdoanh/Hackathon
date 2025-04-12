package com.hacof.hackathon.service.impl;

import org.springframework.stereotype.Service;

import com.hacof.hackathon.constant.TeamRequestMemberStatus;
import com.hacof.hackathon.constant.TeamRequestStatus;
import com.hacof.hackathon.entity.TeamRequest;
import com.hacof.hackathon.entity.TeamRequestMember;
import com.hacof.hackathon.entity.User;
import com.hacof.hackathon.service.EmailService;
import com.hacof.hackathon.service.NotificationService;
import com.hacof.hackathon.util.SecurityUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    private final EmailService emailService;
    private final SecurityUtil securityUtil;

    @Override
    public void notifyTeamRequestCreated(TeamRequest request) {
        String emailContent = String.format(
                "You have been invited to join team %s in hackathon %s. Please respond to this invitation within %d days.",
                request.getName(), request.getHackathon().getTitle(), 7);

        // Collect all valid emails
        List<String> memberEmails = request.getTeamRequestMembers().stream()
                .map(member -> member.getUser())
                .filter(Objects::nonNull)
                .map(User::getEmail)
                .filter(email -> email != null && !email.trim().isEmpty())
                .collect(Collectors.toList());

        if (!memberEmails.isEmpty()) {
            try {
                // Send all emails in one BCC message
                emailService.sendBulkEmails(memberEmails, "Team Invitation", emailContent);
                log.info("Sent team invitation to {} members", memberEmails.size());
            } catch (Exception e) {
                log.error("Failed to send team invitation emails: {}", e.getMessage());
            }
        } else {
            log.warn("No valid emails found for team members");
        }
    }

    @Override
    public void notifyMemberResponse(TeamRequestMember member) {
        String status = member.getStatus() == TeamRequestMemberStatus.APPROVED ? "APPROVED" : "REJECTED";
        String emailContent = String.format(
                "Member %s has %s your team invitation!",
                member.getUser().getUsername(),
                status);

        try {
            // Single email - keep using individual send
            emailService.sendEmail(
                    member.getTeamRequest().getCreatedBy().getEmail(),
                    "Team Invitation Response",
                    emailContent
            );
        } catch (Exception e) {
            log.error("Failed to send member response notification", e);
        }
    }

    @Override
    public void notifyTeamRequestReviewed(TeamRequest request) {
        String status = request.getStatus() == TeamRequestStatus.APPROVED ? "approved" : "rejected";
        String reason = request.getStatus() == TeamRequestStatus.REJECTED ?
                "\nReason: " + request.getNote() : "";

        String emailContent = String.format(
                "Your team request for %s has been %s.%s",
                request.getHackathon().getTitle(),
                status,
                reason);

        // Collect all emails (creator + members)
        List<String> allRecipients = new ArrayList<>();

        // Add creator email if valid
        if (request.getCreatedBy() != null &&
                request.getCreatedBy().getEmail() != null &&
                !request.getCreatedBy().getEmail().trim().isEmpty()) {
            allRecipients.add(request.getCreatedBy().getEmail());
        }

        // Add member emails
        request.getTeamRequestMembers().stream()
                .map(member -> member.getUser())
                .filter(Objects::nonNull)
                .map(User::getEmail)
                .filter(email -> email != null && !email.trim().isEmpty())
                .forEach(allRecipients::add);

        if (!allRecipients.isEmpty()) {
            try {
                // Send all notifications in one BCC message
                emailService.sendBulkEmails(
                        allRecipients,
                        "Team Request Update",
                        emailContent
                );
                log.info("Sent team request update to {} recipients", allRecipients.size());
            } catch (Exception e) {
                log.error("Failed to send team request update notifications", e);
            }
        } else {
            log.warn("No valid emails found for team request update");
        }
    }
}
