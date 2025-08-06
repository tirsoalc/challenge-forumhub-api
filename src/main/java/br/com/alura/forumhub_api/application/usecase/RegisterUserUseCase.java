package br.com.alura.forumhub_api.application.usecase;

import br.com.alura.forumhub_api.api.dto.request.RegisterRequest;
import br.com.alura.forumhub_api.domain.entity.User;
import br.com.alura.forumhub_api.domain.repository.UserRepository;
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
        validateEmailDoesNotExist(request.email());
        
        String encodedPassword = passwordEncoder.encode(request.password());
        
        User user = new User(request.name(), request.email(), encodedPassword);
        return userRepository.save(user);
    }
    
    private void validateEmailDoesNotExist(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Já existe um usuário com este email");
        }
    }
}