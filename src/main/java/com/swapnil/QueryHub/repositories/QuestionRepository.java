package com.swapnil.QueryHub.repositories;

import com.swapnil.QueryHub.models.Question;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import org.springframework.data.domain.Pageable;

@Repository
public interface QuestionRepository extends ReactiveMongoRepository<Question,String> {
    @Query("{'$or' : [" +
            "{'title' : { $regex : ?0, $options : 'i' }}, " +
            "{'content' : { $regex : ?0, $options : 'i' }}]" +
            "}")
    Flux<Question>findByTitleOrContentContainingIgnorecase(String searchTerm, Pageable pageable);
}
