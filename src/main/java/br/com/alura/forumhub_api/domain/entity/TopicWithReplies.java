package br.com.alura.forumhub_api.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TopicWithReplies {
    private final Topic topic;
    private final List<Reply> replies;
}