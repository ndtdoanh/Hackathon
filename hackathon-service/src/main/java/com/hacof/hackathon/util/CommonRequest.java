package com.hacof.hackathon.util;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonRequest<T> {
    @Size(max = 36)
    private String requestId;

    @JsonDeserialize(using = CustomLocalDateTimeDeserialized.class)
    private LocalDateTime requestDateTime;

    @Size(max = 30)
    private String channel;

    @Valid
    private T data;

    public CommonRequest(T data) {
        this.data = data;
    }
}
