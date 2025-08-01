package br.com.alura.forumhub_api.api.controller;

import br.com.alura.forumhub_api.api.dto.request.CreateTopicRequest;
import br.com.alura.forumhub_api.api.dto.request.UpdateTopicRequest;
import br.com.alura.forumhub_api.api.dto.response.TopicListResponse;
import br.com.alura.forumhub_api.api.dto.response.TopicResponse;
import br.com.alura.forumhub_api.application.usecase.*;
import br.com.alura.forumhub_api.domain.entity.PageRequest;
import br.com.alura.forumhub_api.domain.entity.PageResponse;
import br.com.alura.forumhub_api.domain.entity.Topic;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicController {
    
    private final CreateTopicUseCase createTopicUseCase;
    private final ListTopicsUseCase listTopicsUseCase;
    private final GetTopicByIdUseCase getTopicByIdUseCase;
    private final UpdateTopicUseCase updateTopicUseCase;
    private final DeleteTopicUseCase deleteTopicUseCase;
    
    public TopicController(CreateTopicUseCase createTopicUseCase,
                          ListTopicsUseCase listTopicsUseCase,
                          GetTopicByIdUseCase getTopicByIdUseCase,
                          UpdateTopicUseCase updateTopicUseCase,
                          DeleteTopicUseCase deleteTopicUseCase) {
        this.createTopicUseCase = createTopicUseCase;
        this.listTopicsUseCase = listTopicsUseCase;
        this.getTopicByIdUseCase = getTopicByIdUseCase;
        this.updateTopicUseCase = updateTopicUseCase;
        this.deleteTopicUseCase = deleteTopicUseCase;
    }
    
    @PostMapping
    public ResponseEntity<TopicResponse> createTopic(@RequestBody @Valid CreateTopicRequest request,
                                                    UriComponentsBuilder uriBuilder) {
        Topic createdTopic = createTopicUseCase.execute(request);
        TopicResponse response = TopicResponse.fromDomain(createdTopic);
        
        URI location = uriBuilder.path("/topicos/{id}")
                .buildAndExpand(createdTopic.getId())
                .toUri();
        
        return ResponseEntity.created(location).body(response);
    }
    
    @GetMapping
    public ResponseEntity<PageResponse<TopicListResponse>> listTopicsWithPagination(
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable) {
        
        PageRequest pageRequest = new PageRequest(
            pageable.getPageNumber(),
            pageable.getPageSize(),
            pageable.getSort().iterator().hasNext() ? 
                pageable.getSort().iterator().next().getProperty() : "createdAt",
            pageable.getSort().iterator().hasNext() ? 
                pageable.getSort().iterator().next().getDirection().name() : "ASC"
        );
        
        PageResponse<Topic> topicsPage = listTopicsUseCase.executeWithPagination(pageRequest);
        
        List<TopicListResponse> responseContent = topicsPage.getContent()
                .stream()
                .map(TopicListResponse::fromDomain)
                .toList();
                
        PageResponse<TopicListResponse> response = new PageResponse<>(
            responseContent,
            topicsPage.getPage(),
            topicsPage.getSize(),
            topicsPage.getTotalElements()
        );
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TopicResponse> getTopicById(@PathVariable Long id) {
        Topic topic = getTopicByIdUseCase.execute(id);
        TopicResponse response = TopicResponse.fromDomain(topic);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<TopicResponse> updateTopic(@PathVariable Long id, 
                                                    @RequestBody @Valid UpdateTopicRequest request) {
        Topic updatedTopic = updateTopicUseCase.execute(id, request);
        TopicResponse response = TopicResponse.fromDomain(updatedTopic);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        deleteTopicUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<TopicListResponse>> listAllTopics() {
        List<Topic> topics = listTopicsUseCase.execute();
        List<TopicListResponse> response = topics.stream()
                .map(TopicListResponse::fromDomain)
                .toList();
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/limited")
    public ResponseEntity<List<TopicListResponse>> listTopicsWithLimit(
            @RequestParam(defaultValue = "10") int limit) {
        List<Topic> topics = listTopicsUseCase.executeWithLimit(limit);
        List<TopicListResponse> response = topics.stream()
                .map(TopicListResponse::fromDomain)
                .toList();
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<TopicListResponse>> listTopicsByCourseAndYear(
            @RequestParam String curso,
            @RequestParam Integer ano) {
        List<Topic> topics = listTopicsUseCase.executeByCourseAndYear(curso, ano);
        List<TopicListResponse> response = topics.stream()
                .map(TopicListResponse::fromDomain)
                .toList();
        return ResponseEntity.ok(response);
    }
}