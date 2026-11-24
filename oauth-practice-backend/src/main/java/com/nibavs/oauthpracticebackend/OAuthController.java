package com.nibavs.oauthpracticebackend;

import com.nibavs.oauthpracticebackend.dtos.JsonCodeRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OAuthController {

    private final OAuthService oAuthService;

    @GetMapping("/status")
    public ResponseEntity<String> getStatus() {
        return ResponseEntity.ok("Live ");
    }

    @GetMapping("/google/uri")
    public ResponseEntity<String> getGoogleOAuthRedirectUri() {
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(oAuthService.generateGoogleOAuthRedirectUri()))
                .build();
    }

    @PostMapping("/google/callback")
    public ResponseEntity<Object> handleGoogleCode(@NonNull @RequestBody JsonCodeRequest jsonCodeRequest) {
        return ResponseEntity.ok(oAuthService.getTokenFromGoogleCode(jsonCodeRequest.code()));
    }
}
