package com.hacof.hackathon.service.impl;

import com.hacof.hackathon.exception.InvalidInputException;
import org.springframework.stereotype.Service;

import com.hacof.hackathon.constant.TeamRequestMemberStatus;
import com.hacof.hackathon.constant.TeamRequestStatus;
import com.hacof.hackathon.entity.TeamRequest;
import com.hacof.hackathon.entity.TeamRequestMember;
import com.hacof.hackathon.entity.User;
import com.hacof.hackathon.service.EmailService;
import com.hacof.hackathon.service.NotificationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    private final EmailService emailService;

    @Override
    public void notifyTeamRequestCreated(TeamRequest request) {
        validateTeamRequest(request);

        String emailContent = String.format(
                """
                <html>
                <head>
                    <style>
                        body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                        .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                        .header { background-color: #4a6baf; color: white; padding: 15px; text-align: center; border-radius: 5px 5px 0 0; }
                        .content { padding: 20px; background-color: #f9f9f9; border-radius: 0 0 5px 5px; }
                        .button { display: inline-block; padding: 10px 20px; background-color: #4a6baf; color: white; text-decoration: none; border-radius: 4px; margin: 10px 0; }
                        .footer { margin-top: 20px; font-size: 0.9em; color: #777; }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <div class="header">
                            <h2>Team Invitation</h2>
                        </div>
                        <div class="content">
                            <p>Hello Team Member,</p>
                            <p>You have been invited to join <strong>%s</strong> in the hackathon <strong>%s</strong>.</p>
                            <p>Please respond to this invitation within <strong>%d days</strong>.</p>
                            <div style="text-align: center; margin: 25px 0;">
                                <a href="[YOUR_ACCEPT_LINK_HERE]" class="button">Accept Invitation</a>
                                <a href="[YOUR_REJECT_LINK_HERE]" class="button" style="background-color: #e74c3c;">Decline</a>
                            </div>
                            <p>If the buttons don't work, you can also reply to this email with your decision.</p>
                            <div class="footer">
                                <p>Best regards,<br>Hackathon Team</p>
                            </div>
                        </div>
                    </div>
                </body>
                </html>
                """,
                escapeHtml(request.getName()),
                escapeHtml(request.getHackathon().getTitle()),
                7
        );

        List<String> memberEmails = extractMemberEmails(request);

        if (!memberEmails.isEmpty()) {
            try {
                emailService.sendBulkHtmlEmails(memberEmails, "🎯 Team Invitation: " + request.getName(), emailContent);
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
                <html>
                <head>
                    <style>
                        body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                        .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                        .header { background-color: #4a6baf; color: white; padding: 15px; text-align: center; border-radius: 5px 5px 0 0; }
                        .content { padding: 20px; background-color: #f9f9f9; border-radius: 0 0 5px 5px; }
                        .status-box { background-color: #f0f0f0; padding: 15px; border-left: 4px solid #4a6baf; margin: 15px 0; }
                        .footer { margin-top: 20px; font-size: 0.9em; color: #777; }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <div class="header">
                            <h2>Team Invitation Response</h2>
                        </div>
                        <div class="content">
                            <p>Dear Team Leader,</p>
                            <p>Member <strong>%s</strong> has <strong style="color: %s">%s</strong> your team invitation for <strong>%s</strong> in hackathon <strong>%s</strong>.</p>
                            
                            <div class="status-box">
                                <h3>Current Team Status</h3>
                                <ul>
                                    <li>Total invited: %d</li>
                                    <li>✅ Approved: %d</li>
                                    <li>⏳ Pending: %d</li>
                                    <li>❌ Rejected: %d</li>
                                </ul>
                            </div>
                            
                            <p>You can view the full team status on your <a href="[YOUR_DASHBOARD_LINK]">dashboard</a>.</p>
                            
                            <div class="footer">
                                <p>Best regards,<br>Hackathon Team</p>
                            </div>
                        </div>
                    </div>
                </body>
                </html>
                """,
                escapeHtml(member.getUser().getUsername()),
                member.getStatus() == TeamRequestMemberStatus.APPROVED ? "#2ecc71" : "#e74c3c",
                member.getStatus() == TeamRequestMemberStatus.APPROVED ? "APPROVED" : "REJECTED",
                escapeHtml(member.getTeamRequest().getName()),
                escapeHtml(member.getTeamRequest().getHackathon().getTitle()),
                member.getTeamRequest().getTeamRequestMembers().size(),
                countMembersByStatus(member.getTeamRequest(), TeamRequestMemberStatus.APPROVED),
                countMembersByStatus(member.getTeamRequest(), TeamRequestMemberStatus.PENDING),
                countMembersByStatus(member.getTeamRequest(), TeamRequestMemberStatus.REJECTED)
        );

        String creatorEmail = extractCreatorEmail(member.getTeamRequest());

        if (creatorEmail != null) {
            try {
                emailService.sendHtmlEmail(creatorEmail, "📢 Team Invitation Response", emailContent);
                log.debug("Sent member response notification to creator: {}", creatorEmail);
            } catch (Exception e) {
                log.error("Failed to send member response notification", e);
            }
        }
    }

    @Override
    public void notifyTeamRequestReviewed(TeamRequest request) {
        validateTeamRequest(request);

        String status = request.getStatus() == TeamRequestStatus.APPROVED ? "approved" : "rejected";
        String statusColor = request.getStatus() == TeamRequestStatus.APPROVED ? "#2ecc71" : "#e74c3c";

        String emailContent = String.format(
                """
                <html>
                <head>
                    <style>
                        body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                        .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                        .header { background-color: %s; color: white; padding: 15px; text-align: center; border-radius: 5px 5px 0 0; }
                        .content { padding: 20px; background-color: #f9f9f9; border-radius: 0 0 5px 5px; }
                        .next-steps { background-color: #f0f0f0; padding: 15px; margin: 15px 0; border-radius: 4px; }
                        .footer { margin-top: 20px; font-size: 0.9em; color: #777; }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <div class="header" style="background-color: %s">
                            <h2>Team Request %s</h2>
                        </div>
                        <div class="content">
                            <p>Dear Team Members,</p>
                            <p>Your team request <strong>"%s"</strong> for hackathon <strong>"%s"</strong> has been <strong style="color: %s">%s</strong>.</p>
                           
                            %s
                            
                            <div class="next-steps">
                                <h3>Next Steps</h3>
                                %s
                            </div>
                            
                            <p>If you have any questions, please reply to this email.</p>
                            
                            <div class="footer">
                                <p>Best regards,<br>Hackathon Organizers</p>
                            </div>
                        </div>
                    </div>
                </body>
                </html>
                """,
                statusColor,
                statusColor,
                request.getStatus() == TeamRequestStatus.APPROVED ? "Approved" : "Rejected",
                escapeHtml(request.getName()),
                escapeHtml(request.getHackathon().getTitle()),
                statusColor,
                status,
                request.getStatus() == TeamRequestStatus.REJECTED ?
                        "<p><strong>Reason:</strong> " + escapeHtml(request.getNote()) + "</p>" : "",
                request.getStatus() == TeamRequestStatus.APPROVED ?
                        """
                        <ul>
                            <li>✅ <strong>Prepare for the hackathon!</strong></li>
                            <li>📅 Check the event schedule</li>
                            <li>👥 Complete your team profile</li>
                        </ul>
                        """ :
                        """
                        <ul>
                            <li>🔄 You may submit another request if allowed</li>
                            <li>📧 Contact organizers for more information</li>
                        </ul>
                        """
        );

        List<String> allRecipients = extractAllRecipientEmails(request);

        if (!allRecipients.isEmpty()) {
            try {
                String subject = request.getStatus() == TeamRequestStatus.APPROVED ?
                        "🎉 Congratulations! Team Approved: " + request.getName() :
                        "⚠️ Team Request Update: " + request.getName();

                emailService.sendBulkHtmlEmails(allRecipients, subject, emailContent);
                log.info("Sent team status update to {} recipients", allRecipients.size());
            } catch (Exception e) {
                log.error("Failed to send team status update", e);
            }
        }
    }

    // Helper methods
    private String escapeHtml(String input) {
        if (input == null) {
            return "";
        }
        return input.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

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
                        .filter(email -> email != null && !email.trim().isEmpty())
        ).distinct().collect(Collectors.toList());
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
}
