package br.com.alura.forumhub_api.api.dto.response;

public record TokenResponse(
        String token,
        String type,
        Long expiresIn
) {
    public TokenResponse(String token, Long expiresIn) {
        this(token, "Bearer", expiresIn);
    }
}