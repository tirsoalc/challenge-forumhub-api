package br.com.alura.forumhub_api.application.usecase;

import br.com.alura.forumhub_api.api.dto.request.RegisterRequest;
import br.com.alura.forumhub_api.domain.entity.User;
import br.com.alura.forumhub_api.domain.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserUseCase {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public RegisterUserUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    public User execute(RegisterRequest request) {
        validateUserNotAuthenticated();
        validateEmailDoesNotExist(request.email());
        
        String encodedPassword = passwordEncoder.encode(request.password());
        
        User user = new User(request.name(), request.email(), encodedPassword);
        return userRepository.save(user);
    }
    
    private void validateUserNotAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && 
            !authentication.getName().equals("anonymousUser")) {
            throw new AccessDeniedException("Usuário já está autenticado. Faça logout antes de registrar uma nova conta.");
        }
    }
    
    private void validateEmailDoesNotExist(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Já existe um usuário com este email");
        }
    }
}