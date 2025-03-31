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
                "Bạn đã được mời tham gia team %s trong hackathon %s. Vui lòng phản hồi trong vòng %d ngày.",
                request.getName(), request.getHackathon().getTitle(), 7);

        // send email to each member
        request.getTeamRequestMembers().forEach(member -> {
            try {
                User user = member.getUser();
                if (user != null
                        && user.getEmail() != null
                        && !user.getEmail().trim().isEmpty()) {
                    log.debug("Sending email to: {}", user.getEmail());
                    emailService.sendEmail(user.getEmail(), "Lời mời tham gia team", emailContent);
                    log.info("Đã gửi email mời tham gia team cho: {}", user.getEmail());
                } else {
                    log.warn(
                            "Không thể gửi email cho member: {} - Email không hợp lệ hoặc null",
                            user != null ? user.getId() : "unknown");
                }
            } catch (Exception e) {
                log.error(
                        "Lỗi khi gửi email cho member: {} - {}",
                        member.getUser() != null ? member.getUser().getId() : "unknown",
                        e.getMessage());
            }
        });
    }

    @Override
    public void notifyMemberResponse(TeamRequestMember member) {
        String status = member.getStatus() == TeamRequestMemberStatus.APPROVED ? "đồng ý" : "từ chối";
        String emailContent = String.format(
                "Thành viên %s đã %s tham gia team của bạn.", member.getUser().getUsername(), status);

        try {
            emailService.sendEmail(
                    member.getTeamRequest().getCreatedBy().getEmail(), "Phản hồi tham gia team", emailContent);
        } catch (Exception e) {
            log.error("Failed to send notification about member response", e);
        }
    }

    @Override
    public void notifyTeamRequestReviewed(TeamRequest request) {
        String status = request.getStatus() == TeamRequestStatus.APPROVED ? "chấp nhận" : "từ chối";
        String reason = request.getStatus() == TeamRequestStatus.REJECTED ? "\nLý do: " + request.getNote() : "";

        String emailContent = String.format("Yêu cầu tạo team của bạn đã được %s.%s", status, reason);

        // Gửi email cho người tạo request
        try {
            emailService.sendEmail(request.getCreatedBy().getEmail(), "Kết quả xét duyệt team", emailContent);
        } catch (Exception e) {
            log.error("Failed to send notification to request creator", e);
        }

        // Gửi email cho các thành viên
        request.getTeamRequestMembers().forEach(member -> {
            try {
                emailService.sendEmail(member.getUser().getEmail(), "Kết quả xét duyệt team", emailContent);
            } catch (Exception e) {
                log.error("Failed to send notification to team member", e);
            }
        });
    }
}
