package br.com.alura.forumhub_api.api.controller;

import br.com.alura.forumhub_api.api.dto.request.LoginRequest;
import br.com.alura.forumhub_api.api.dto.response.TokenResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authToken = 
            new UsernamePasswordAuthenticationToken(
                loginRequest.email(), 
                loginRequest.password()
            );

        Authentication authentication = authenticationManager.authenticate(authToken);

        // TODO: gerar o token jwt com no token service;
        // retornando um placeholder por enquanto
        TokenResponse tokenResponse = new TokenResponse("placeholder-token", 3600L);
        
        return ResponseEntity.ok(tokenResponse);
    }
}