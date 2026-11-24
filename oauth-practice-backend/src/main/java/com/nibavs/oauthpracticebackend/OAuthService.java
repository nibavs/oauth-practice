package com.nibavs.oauthpracticebackend;

import com.nibavs.oauthpracticebackend.config.AppProperties;
import com.nibavs.oauthpracticebackend.dtos.GoogleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OAuthService {
    private final RestClient restClient;
    private final AppProperties appProperties;


    public String generateGoogleOAuthRedirectUri() {
        List<String> scopes = List.of(
                "https://www.googleapis.com/auth/drive",
                "https://www.googleapis.com/auth/calendar",
                "openid",
                "profile",
                "email"
        );

        return UriComponentsBuilder
                .fromUriString("https://accounts.google.com/o/oauth2/v2/auth")
                .queryParam("client_id", appProperties.OAUTH_GOOGLE_CLIENT_ID())
                .queryParam("redirect_uri", "http://localhost:3000/auth/google")
                .queryParam("response_type", "code")
                .queryParam("scope", String.join(" ", scopes))
                .queryParam("access_type", "offline")
                .queryParam("prompt", "consent")
                .build()
                .encode()
                .toUriString();
    }


    public void getTokenFromGoogleCode(String code) {
        var response = restClient.post()
                .body(new GoogleRequest(
                        appProperties.OAUTH_GOOGLE_CLIENT_ID(),
                        appProperties.OAUTH_GOOGLE_CLIENT_SECRET(),
                        code,
                        appProperties.authorizationCode(),
                        appProperties.redirectUrl()

                ))
                .retrieve()
                .body(Object.class);
        System.out.println(response);
    }
}
