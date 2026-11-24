package com.nibavs.oauthpracticebackend;

import com.nibavs.oauthpracticebackend.config.AppProperties;
import com.nibavs.oauthpracticebackend.dtos.GoogleRequest;
import com.nibavs.oauthpracticebackend.dtos.GoogleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import tools.jackson.databind.ObjectMapper;

import java.util.Base64;
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


    public Object getTokenFromGoogleCode(String code) {
        GoogleRequest request = new GoogleRequest(
                appProperties.OAUTH_GOOGLE_CLIENT_ID(),
                appProperties.OAUTH_GOOGLE_CLIENT_SECRET(),
                code,
                appProperties.authorizationCode(),
                appProperties.redirectUrl()

        );

        var response = restClient.post()
                .body(request)
                .retrieve()
                .body(GoogleResponse.class);

        String[] chunks = response.idToken().split("\\.");
        byte[] decodedPayload = Base64.getUrlDecoder().decode(chunks[1]);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(decodedPayload);
    }
//    {access_token=token, expires_in=time, refresh_token=token, scope=https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/drive https://www.googleapis.com/auth/calendar https://www.googleapis.com/auth/userinfo.profile openid, token_type=Bearer, id_token=token, refresh_token_expires_in=604789
}
