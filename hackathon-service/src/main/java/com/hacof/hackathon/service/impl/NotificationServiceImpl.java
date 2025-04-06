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

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    private final EmailService emailService;
    private final SecurityUtil securityUtil;

    @Override
    public void notifyTeamRequestCreated(TeamRequest request) {
        String emailContent = String.format(
                "You have invited from team %s in hackathon %s. Please reply this mail in %d days.",
                request.getName(), request.getHackathon().getTitle(), 7);

        // send email to each member
        request.getTeamRequestMembers().forEach(member -> {
            try {
                User user = member.getUser();
                if (user != null
                        && user.getEmail() != null
                        && !user.getEmail().trim().isEmpty()) {
                    log.debug("Sending email to: {}", user.getEmail());
                    emailService.sendEmail(user.getEmail(), "Team Invitation", emailContent);
                    log.info("Sent email to : {}", user.getEmail());
                } else {
                    log.warn(
                            "Cannot sent email: {} - Email was invalid or null",
                            user != null ? user.getId() : "unknown");
                }
            } catch (Exception e) {
                log.error(
                        "Error when send mail to member: {} - {}",
                        member.getUser() != null ? member.getUser().getId() : "unknown",
                        e.getMessage());
            }
        });
    }

    @Override
    public void notifyMemberResponse(TeamRequestMember member) {
        String status = member.getStatus() == TeamRequestMemberStatus.APPROVED ? "APPROVE" : "REJECT";
        String emailContent = String.format(
                "Member %s was %s joined your team!", member.getUser().getUsername(), status);

        try {
            emailService.sendEmail(member.getTeamRequest().getCreatedBy().getEmail(), "Reply Join Team", emailContent);
        } catch (Exception e) {
            log.error("Failed to send notification about member response", e);
        }
    }

    @Override
    public void notifyTeamRequestReviewed(TeamRequest request) {
        String status = request.getStatus() == TeamRequestStatus.APPROVED ? "approved" : "rejected";
        String reason = request.getStatus() == TeamRequestStatus.REJECTED ? "\nReason: " + request.getNote() : "";

        String emailContent = String.format("Team Request was created %s.%s", status, reason);

        // Gửi email cho người tạo request
        try {
            emailService.sendEmail(request.getCreatedBy().getEmail(), "Team Invitation Result", emailContent);
        } catch (Exception e) {
            log.error("Failed to send notification to request creator", e);
        }

        // Gửi email cho các thành viên
        request.getTeamRequestMembers().forEach(member -> {
            try {
                emailService.sendEmail(member.getUser().getEmail(), "Team Request Result", emailContent);
            } catch (Exception e) {
                log.error("Failed to send notification to team member", e);
            }
        });
    }
}
