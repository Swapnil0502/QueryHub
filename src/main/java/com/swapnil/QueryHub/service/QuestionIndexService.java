package com.swapnil.QueryHub.service;

import com.swapnil.QueryHub.models.Question;
import com.swapnil.QueryHub.models.QuestionElasticDocument;
import com.swapnil.QueryHub.repositories.QuestionDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionIndexService implements IQuestionIndexService{

    private final QuestionDocumentRepository questionDocumentRepository;

    @Override
    public void createQuestionIndex(Question question){
        QuestionElasticDocument doc = QuestionElasticDocument.builder()
                .id(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .build();

        questionDocumentRepository.save(doc);
    }
}
