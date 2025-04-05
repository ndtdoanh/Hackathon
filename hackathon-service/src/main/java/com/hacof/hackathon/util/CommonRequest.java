package com.hacof.hackathon.util;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommonRequest<T> {
    //    @NotNull
    //    @Size(max = 36)
    // private String requestId;

    //    @NotNull
    // @JsonDeserialize(using = CustomLocalDateTimeDeserialized.class)
    // private LocalDateTime requestDateTime;

    //    @NotNull
    //    @Size(max = 30)
    // private String channel;

    // @Valid
    @JsonUnwrapped
    private T data;
}
