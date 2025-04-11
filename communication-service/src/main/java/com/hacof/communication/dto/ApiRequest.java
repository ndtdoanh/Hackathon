package com.hacof.communication.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

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
