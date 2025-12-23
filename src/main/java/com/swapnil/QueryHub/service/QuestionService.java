package com.swapnil.QueryHub.service;

import com.swapnil.QueryHub.Dto.QuestionRequestDto;
import com.swapnil.QueryHub.Dto.QuestionResponseDto;
import com.swapnil.QueryHub.models.Question;
import com.swapnil.QueryHub.repositories.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class QuestionService implements IQuestionService{

    private final QuestionRepository questionRepository;
    private final ModelMapper modelMapper;

    @Override
    public Flux<QuestionResponseDto>searchQuestion(String searchTerm, Pageable pageable){

     return questionRepository.findByTitleOrContentContainingIgnorecase(searchTerm, pageable).map(question -> modelMapper.map(question,QuestionResponseDto.class));
    }

    @Override
    public Mono<QuestionResponseDto>createQuestion(QuestionRequestDto req){
        return questionRepository.save(modelMapper.map(req,Question.class)).map(question -> modelMapper.map(question, QuestionResponseDto.class));
    }
}
