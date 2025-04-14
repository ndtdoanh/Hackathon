package com.hacof.communication.util;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hacof.communication.dto.CustomLocalDateTimeDeserialized;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse<T> {
    private String requestId;

    @JsonDeserialize(using = CustomLocalDateTimeDeserialized.class)
    private LocalDateTime requestDateTime;

    private String channel;

    private int status;
    private String message;
    private T data;
    private List<String> errors;

    private Result result;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Result {
        private String responseCode;
        private String description;
    }
}
