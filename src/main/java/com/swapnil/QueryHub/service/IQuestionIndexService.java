package com.swapnil.QueryHub.service;

import com.swapnil.QueryHub.Dto.QuestionResponseDto;
import com.swapnil.QueryHub.models.Question;

import java.util.List;

public interface IQuestionIndexService {

    void createQuestionIndex(Question question);
}
