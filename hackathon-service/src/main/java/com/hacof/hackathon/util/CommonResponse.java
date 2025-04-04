package com.hacof.hackathon.util;

import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse<T> {
    //    private String requestId;
    //
    //    @JsonDeserialize(using = CustomLocalDateTimeDeserialized.class)
    //    private LocalDateTime requestDateTime;
    //
    //    private String channel;
    private Result result;
    private T data;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Result {
        private String responseCode;
        private String description;
    }

    @JsonGetter("message")
    public String getMessage() {
        return (result != null) ? result.getDescription() : null;
    }
}
