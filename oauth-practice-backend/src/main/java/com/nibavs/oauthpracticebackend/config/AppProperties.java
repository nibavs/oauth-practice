package com.nibavs.oauthpracticebackend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public record AppProperties(
        String OAUTH_GOOGLE_CLIENT_SECRET,
        String OAUTH_GOOGLE_CLIENT_ID,
        String authorizationCode,
        String redirectUrl

) {}
