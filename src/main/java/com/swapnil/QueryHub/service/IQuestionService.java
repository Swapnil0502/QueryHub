package com.swapnil.QueryHub.service;

import com.swapnil.QueryHub.Dto.QuestionRequestDto;
import com.swapnil.QueryHub.Dto.QuestionResponseDto;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IQuestionService {
    Flux<QuestionResponseDto>searchQuestion(String searchTerm, Pageable pageable);

    Mono<QuestionResponseDto> createQuestion(QuestionRequestDto req);

    Flux<QuestionResponseDto>getAllQuestions(String cursor, int size);

    Mono<QuestionResponseDto> getQuestionById(String id);

    List<QuestionResponseDto> searchQuestionByElasticSearch(String query);
}
