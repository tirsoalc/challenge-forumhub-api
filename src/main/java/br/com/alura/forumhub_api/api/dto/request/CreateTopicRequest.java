package br.com.alura.forumhub_api.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateTopicRequest(
        @NotBlank(message = "Título é obrigatório")
        String title,
        
        @NotBlank(message = "Mensagem é obrigatória")
        String message,
        
        @NotNull(message = "ID do curso é obrigatório")
        Long courseId
) {
}