package com.hacof.hackathon.util;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

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
    @JsonSerialize(using = CustomLocalDateTimeSerialize.class)
    private LocalDateTime requestDateTime;

    private String channel;
    private Result result;
    private T data;

    @Override
    public String toString() {
        return "CommonResponse{" + "requestId='"
                + requestId + '\'' + ", requestDateTime="
                + requestDateTime + ", channel='"
                + channel + '\'' + ", result="
                + result + ", data="
                + data + '}';
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Result {
        private String responseCode;
        private String description;

        @Override
        public String toString() {
            return "Result{" + "responseCode='" + responseCode + '\'' + ", description='" + description + '\'' + '}';
        }
    }

    @JsonGetter("message")
    public String getMessage() {
        return (result != null) ? result.getDescription() : null;
    }
}
