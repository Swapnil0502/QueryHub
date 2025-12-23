package com.swapnil.QueryHub.service;

import com.swapnil.QueryHub.Dto.QuestionRequestDto;
import com.swapnil.QueryHub.Dto.QuestionResponseDto;
import com.swapnil.QueryHub.models.Question;
import com.swapnil.QueryHub.repositories.QuestionRepository;
import com.swapnil.QueryHub.utils.CursorUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class QuestionService implements IQuestionService{

    private final QuestionRepository questionRepository;
    private final ModelMapper modelMapper;

    @Override
    public Flux<QuestionResponseDto>searchQuestion(String searchTerm, Pageable pageable){

     return questionRepository.findByTitleOrContentContainingIgnoreCase(searchTerm, pageable).map(question -> modelMapper.map(question,QuestionResponseDto.class));
    }

    @Override
    public Mono<QuestionResponseDto>createQuestion(QuestionRequestDto req){
        return questionRepository.save(modelMapper.map(req,Question.class)).map(question -> modelMapper.map(question, QuestionResponseDto.class));
    }

    @Override
    public Flux<QuestionResponseDto>getAllQuestions(String cursor, int size){
        Pageable pageable = PageRequest.of(0,size);

        if(!CursorUtils.isValidCursor(cursor)){
            return questionRepository.findAllByOrderByCreatedAtDesc(pageable)
                    .map(question -> modelMapper.map(question, QuestionResponseDto.class))
                    .doOnError(error -> System.out.println("Error fetching questions: " + error))
                    .doOnComplete(() -> System.out.println("Question fetched successfully"));
        }
        else{
            Instant curTimestamp = CursorUtils.parseCursor(cursor);
            return questionRepository.findByCreatedAtLessThanOrderByCreatedAtDesc(curTimestamp, pageable)
                    .map(question -> modelMapper.map(question, QuestionResponseDto.class))
                    .doOnError(error -> System.out.println("Error fetching questions: " + error))
                    .doOnComplete(() -> System.out.println("Question fetched successfully"));
        }
    }
}
