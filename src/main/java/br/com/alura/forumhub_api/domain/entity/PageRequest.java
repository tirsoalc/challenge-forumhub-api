package br.com.alura.forumhub_api.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageRequest {
    private final int page;
    private final int size;
    private final String sortBy;
    private final String sortDirection;
    
    public int getOffset() {
        return page * size;
    }
}