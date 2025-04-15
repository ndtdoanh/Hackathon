package com.hacof.communication.util;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hacof.communication.dto.CustomLocalDateTimeDeserialized;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommonRequest<T> {
    @Size(max = 36)
    private String requestId;

    @JsonDeserialize(using = CustomLocalDateTimeDeserialized.class)
    private LocalDateTime requestDateTime;

    @Size(max = 30)
    private String channel;

    private T data;
}
