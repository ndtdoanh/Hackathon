package com.hacof.communication.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
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
