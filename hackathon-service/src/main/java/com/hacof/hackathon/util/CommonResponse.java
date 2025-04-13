package com.hacof.hackathon.util;

import com.fasterxml.jackson.annotation.JsonGetter;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommonResponse<T> {
    private String requestId;

    @JsonDeserialize(using = CustomLocalDateTimeDeserialized.class)
    @JsonSerialize(using = CustomLocalDateTimeSerialize.class)
    private LocalDateTime requestDateTime;

    private String channel;
    private Result result;
    private T data;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class Result {
        private String responseCode;
        private String description;
    }

    @JsonGetter("message")
    public String getMessage() {
        return (result != null) ? result.getDescription() : null;
    }

}
