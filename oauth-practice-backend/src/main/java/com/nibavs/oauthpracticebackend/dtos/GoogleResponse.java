package com.nibavs.oauthpracticebackend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GoogleResponse(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("expires_in") Long expiresIn,
        @JsonProperty("refresh_token") String refreshToken,
        @JsonProperty("scope") String scope,
        @JsonProperty("token_type") String tokenType,
        @JsonProperty("id_token") String idToken,
        @JsonProperty("refresh_token_expires_in") Long refreshTokenExpiresIn
//{access_token=token, expires_in=time, refresh_token=token, scope=https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/drive https://www.googleapis.com/auth/calendar https://www.googleapis.com/auth/userinfo.profile openid, token_type=Bearer, id_token=token, refresh_token_expires_in=604789
) {
}
