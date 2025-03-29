package com.hacof.communication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHENTICATED(1001, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    USER_NOT_EXISTED(1002, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHORIZED(1003, "You do not have permission", HttpStatus.FORBIDDEN),

    INVALID_CONVERSATION_REQUEST(1004, "Invalid conversation request", HttpStatus.BAD_REQUEST),
    INVALID_GROUP_CONVERSATION_REQUEST(1005, "Invalid group conversation request", HttpStatus.BAD_REQUEST),
    CANNOT_CREATE_CONVERSATION_WITH_SELF(1006, "Cannot create conversation with self", HttpStatus.BAD_REQUEST),
    NOT_CONVERSATION_CREATOR(1007, "Not conversation creator", HttpStatus.BAD_REQUEST),

    CONVERSATION_NOT_EXISTED(1008, "Conversation not found", HttpStatus.NOT_FOUND),
    USER_NOT_IN_CONVERSATION(1009, "Users are not in the conversation", HttpStatus.FORBIDDEN),

    INVALID_REQUEST(1010, "Invalid request", HttpStatus.BAD_REQUEST),
    CONVERSATION_ALREADY_EXISTS(1011, "Conversation already exist", HttpStatus.BAD_REQUEST),

    CANNOT_ADD_USER_TO_SINGLE_CONVERSATION(1012, "Cannot add user to single conversation", HttpStatus.BAD_REQUEST),
    CANNOT_UPDATE_SINGLE_CONVERSATION(1013, "Cannot update single conversation", HttpStatus.BAD_REQUEST),
    CANNOT_REMOVE_USER_FROM_SINGLE_CONVERSATION(
            1014, "Cannot remove user from single conversation", HttpStatus.BAD_REQUEST),

    USER_ALREADY_IN_CONVERSATION(1015, "User already in conversation", HttpStatus.BAD_REQUEST),
    MESSAGE_NOT_EXISTED(1016, "Message not existed", HttpStatus.BAD_REQUEST),

    NOTIFICATION_NOT_FOUND(1017, "Notification not found", HttpStatus.NOT_FOUND),
    NOTIFICATION_DELIVERY_NOT_FOUND(1018, "Notification delivery not found", HttpStatus.NOT_FOUND),

    INVALID_NOTIFICATION_REQUEST(1019, "Invalid notification request", HttpStatus.BAD_REQUEST),
    INVALID_ROLE(1020, "Invalid role", HttpStatus.BAD_REQUEST),
    NO_USERS_WITH_ROLE(1021, "No users with role", HttpStatus.NOT_FOUND),

    REACTION_NOT_EXISTED(1022, "Reaction not existed", HttpStatus.BAD_REQUEST);

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
