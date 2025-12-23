package com.swapnil.QueryHub.service;

import com.swapnil.QueryHub.Dto.QuestionRequestDto;
import com.swapnil.QueryHub.Dto.QuestionResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.data.domain.Pageable;

public interface IQuestionService {
    Flux<QuestionResponseDto>searchQuestion(String searchTerm, Pageable pageable);

    Mono<QuestionResponseDto> createQuestion(QuestionRequestDto req);
}
