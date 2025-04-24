package com.hacof.analytics.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHENTICATED(1001, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    FEEDBACK_NOT_FOUND(1002, "Feedback not found", HttpStatus.NOT_FOUND),
    FEEDBACK_EXISTED(1003, "Feedback existed", HttpStatus.BAD_REQUEST),
    INVALID_FEEDBACK(1004, "Invalid feedback", HttpStatus.BAD_REQUEST),
    INVALID_FEEDBACK_TYPE(1004, "Invalid feedback type", HttpStatus.BAD_REQUEST),
    FEEDBACK_DETAIL_NOT_FOUND(1005, "Feedback detail not found", HttpStatus.NOT_FOUND),
    FEEDBACK_DETAIL_ALREADY_EXISTS(1006, "Feedback detail already exists", HttpStatus.BAD_REQUEST),
    TEAM_NOT_FOUND(1007, "Team not found", HttpStatus.NOT_FOUND),
    HACKATHON_NOT_FOUND(1008, "Hackathon not found", HttpStatus.NOT_FOUND),
    MENTOR_NOT_FOUND(1009, "Mentor not found", HttpStatus.NOT_FOUND),
    INVALID_TARGET(1010, "Invalid target", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(1011, "You do not have permission", HttpStatus.FORBIDDEN),
    BLOG_NOT_FOUND(1012, "Blog post not found", HttpStatus.NOT_FOUND),
    BLOG_CANNOT_SUBMIT(1013, "Only drafts can be submitted", HttpStatus.BAD_REQUEST),
    BLOG_CANNOT_APPROVE(1014, "Only pending review blogs can be approved", HttpStatus.BAD_REQUEST),
    BLOG_CANNOT_REJECT(1015, "Only pending review blogs can be rejected", HttpStatus.BAD_REQUEST),
    HACKATHON_REQUIRED(1016, "Hackathon required", HttpStatus.BAD_REQUEST),
    FEEDBACK_DETAIL_ALREADY_SUBMITTED(1017, "Feedback detail already submitted", HttpStatus.BAD_REQUEST),
    FEEDBACK_TARGET_REQUIRED(1018, "Feedback target required", HttpStatus.BAD_REQUEST),
    BLOG_SLUG_ALREADY_EXISTS(1019, "Blog post slug already exists", HttpStatus.BAD_REQUEST);

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
