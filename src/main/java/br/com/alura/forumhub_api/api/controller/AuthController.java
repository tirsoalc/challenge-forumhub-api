package br.com.alura.forumhub_api.api.controller;

import br.com.alura.forumhub_api.api.dto.request.LoginRequest;
import br.com.alura.forumhub_api.api.dto.request.RegisterRequest;
import br.com.alura.forumhub_api.api.dto.response.TokenResponse;
import br.com.alura.forumhub_api.api.dto.response.UserResponse;
import br.com.alura.forumhub_api.application.service.TokenService;
import br.com.alura.forumhub_api.application.usecase.RegisterUserUseCase;
import br.com.alura.forumhub_api.domain.entity.User;
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
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final RegisterUserUseCase registerUserUseCase;

    public AuthController(AuthenticationManager authenticationManager, 
                         TokenService tokenService, 
                         RegisterUserUseCase registerUserUseCase) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.registerUserUseCase = registerUserUseCase;
    }

    @PostMapping("/login")
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
    
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid RegisterRequest registerRequest,
                                               UriComponentsBuilder uriBuilder) {
        User user = registerUserUseCase.execute(registerRequest);
        UserResponse userResponse = UserResponse.fromDomain(user);
        
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
        
        return ResponseEntity.created(uri).body(userResponse);
    }
}