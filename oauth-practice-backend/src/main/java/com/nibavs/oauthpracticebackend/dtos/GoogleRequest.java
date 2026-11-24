package com.nibavs.oauthpracticebackend.dtos;

public record GoogleRequest(
        String client_id,
        String client_secret,
        String code,
        String grant_type,
        String redirect_uri
) {
}
