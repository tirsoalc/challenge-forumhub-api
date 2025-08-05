package br.com.alura.forumhub_api.api.controller;

import br.com.alura.forumhub_api.api.dto.request.LoginRequest;
import br.com.alura.forumhub_api.api.dto.response.TokenResponse;
import br.com.alura.forumhub_api.application.service.TokenService;
import br.com.alura.forumhub_api.infra.security.UserPrincipal;
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
    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authToken = 
            new UsernamePasswordAuthenticationToken(
                loginRequest.email(), 
                loginRequest.password()
            );

        Authentication authentication = authenticationManager.authenticate(authToken);

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String token = tokenService.generateToken(userPrincipal.getUser());
        
        TokenResponse tokenResponse = new TokenResponse(token, tokenService.getExpirationTime());
        
        return ResponseEntity.ok(tokenResponse);
    }
}