package com.swapnil.QueryHub.controller;

import com.swapnil.QueryHub.Dto.QuestionRequestDto;
import com.swapnil.QueryHub.Dto.QuestionResponseDto;
import com.swapnil.QueryHub.service.IQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.data.domain.Pageable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class QuestionController {

    private final IQuestionService questionService;

    @GetMapping("/search")
    public Flux<QuestionResponseDto>searchQuestion(@RequestParam String searchTerm,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page,size);
        return questionService.searchQuestion(searchTerm, pageable);
    }

    @PostMapping("/create")
    public Mono<QuestionResponseDto>createQuestion(@RequestBody QuestionRequestDto req){
        return questionService.createQuestion(req);
    }

    @GetMapping
    public Flux<QuestionResponseDto>getAllQuestions(@RequestParam(required = false) String cursor, @RequestParam(defaultValue = "10") int size){
        return questionService.getAllQuestions(cursor, size);
    }
}
