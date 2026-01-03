package com.swapnil.QueryHub.service;

import com.swapnil.QueryHub.Dto.QuestionRequestDto;
import com.swapnil.QueryHub.Dto.QuestionResponseDto;
import com.swapnil.QueryHub.events.ViewCountEvent;
import com.swapnil.QueryHub.models.Question;
import com.swapnil.QueryHub.models.QuestionElasticDocument;
import com.swapnil.QueryHub.producers.KafkaEventProducer;
import com.swapnil.QueryHub.repositories.QuestionDocumentRepository;
import com.swapnil.QueryHub.repositories.QuestionRepository;
import com.swapnil.QueryHub.utils.CursorUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService implements IQuestionService{

    private final QuestionRepository questionRepository;
    private final ModelMapper modelMapper;
    private final KafkaEventProducer kafkaEventProducer;
    private final IQuestionIndexService iQuestionIndexService;
    private final QuestionDocumentRepository questionDocumentRepository;

    @Override
    public Flux<QuestionResponseDto>searchQuestion(String searchTerm, Pageable pageable){

     return questionRepository.findByTitleOrContentContainingIgnoreCase(searchTerm, pageable).map(question -> modelMapper.map(question,QuestionResponseDto.class));
    }

    @Override
    public Mono<QuestionResponseDto>createQuestion(QuestionRequestDto req){
        return questionRepository.save(modelMapper.map(req,Question.class)).map(question -> {
            {
                iQuestionIndexService.createQuestionIndex(question);
                return modelMapper.map(question, QuestionResponseDto.class);
            }
        });
    }

    @Override
    public Flux<QuestionResponseDto>getAllQuestions(String cursor, int size) {
        Pageable pageable = PageRequest.of(0, size);

        if (!CursorUtils.isValidCursor(cursor)) {
            return questionRepository.findAllByOrderByCreatedAtDesc(pageable)
                    .map(question -> modelMapper.map(question, QuestionResponseDto.class))
                    .doOnError(error -> System.out.println("Error fetching questions: " + error))
                    .doOnComplete(() -> System.out.println("Question fetched successfully"));
        } else {
            Instant curTimestamp = CursorUtils.parseCursor(cursor);
            return questionRepository.findByCreatedAtLessThanOrderByCreatedAtDesc(curTimestamp, pageable)
                    .map(question -> modelMapper.map(question, QuestionResponseDto.class))
                    .doOnError(error -> System.out.println("Error fetching questions: " + error))
                    .doOnComplete(() -> System.out.println("Question fetched successfully"));
        }
    }
        @Override
        public Mono<QuestionResponseDto> getQuestionById(String id){
            return questionRepository.findById(id)
                    .map(question -> modelMapper.map(question, QuestionResponseDto.class))
                    .doOnError(error -> System.out.println("Error fetching the question: " + error))
                    .doOnSuccess(response -> {
                        System.out.println("Question Fetched Successfully: " + response);
                        ViewCountEvent viewCountEvent = new ViewCountEvent(id, "question", Instant.now());
                        kafkaEventProducer.publishViewCount(viewCountEvent);
                    });
        }

        @Override
        public List<QuestionResponseDto> searchQuestionByElasticSearch(String query){
        return questionDocumentRepository.findByTitleContainingOrContentContaining(query, query).stream().map(question -> modelMapper.map(question, QuestionResponseDto.class)).toList();
        }
}
