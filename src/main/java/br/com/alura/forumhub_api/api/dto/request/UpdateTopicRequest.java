package br.com.alura.forumhub_api.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateTopicRequest(
        @NotBlank(message = "Título é obrigatório")
        String title,
        
        @NotBlank(message = "Mensagem é obrigatória")
        String message
) {
}