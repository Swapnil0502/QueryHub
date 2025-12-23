package com.swapnil.QueryHub.repositories;

import com.swapnil.QueryHub.models.Question;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.time.LocalDateTime;

@Repository
public interface QuestionRepository extends ReactiveMongoRepository<Question,String> {
    @Query("{'$or' : [" +
            "{'title' : { $regex : ?0, $options : 'i' }}, " +
            "{'content' : { $regex : ?0, $options : 'i' }}]" +
            "}")
    Flux<Question>findByTitleOrContentContainingIgnoreCase(String searchTerm, Pageable pageable);

    Flux<Question>findByCreatedAtLessThanOrderByCreatedAtDesc(Instant curTimestamp, Pageable pageable);

    Flux<Question>findAllByOrderByCreatedAtDesc(Pageable pageable);
}
