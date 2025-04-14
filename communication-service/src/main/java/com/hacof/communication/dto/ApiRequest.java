package com.hacof.communication.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiRequest<T> {
    @Size(max = 36)
    String requestId;

    @JsonDeserialize(using = CustomLocalDateTimeDeserialized.class)
    LocalDateTime requestDateTime;

    @Size(max = 30)
    String channel;

    String message;
    T data;
}
