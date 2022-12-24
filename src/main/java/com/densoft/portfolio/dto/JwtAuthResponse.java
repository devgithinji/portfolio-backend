package com.densoft.portfolio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class JwtAuthResponse {
    @JsonProperty("token")
    private String accessToken;
    @JsonProperty("user")
    private UserResponse userResponse;

    public JwtAuthResponse(String accessToken, UserResponse userResponse) {
        this.accessToken = "Bearer "+accessToken;
        this.userResponse = userResponse;
    }
}
