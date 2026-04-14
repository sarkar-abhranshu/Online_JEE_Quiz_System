package com.quiz.service;

import com.quiz.model.Question;
import com.quiz.repository.QuestionRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
    Service class for Question-related operations.
*/
@Service
@Transactional
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question addQuestion(Question question) {
        return questionRepository.save(question);
    }

    public Question getQuestionById(Long id) {
        return questionRepository
            .findById(id)
            .orElseThrow(() ->
                new RuntimeException("Question not found with id: " + id)
            );
    }

    public List<Question> getQuestionsByQuizId(Long quizId) {
        return questionRepository.findByQuizId(quizId);
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    public Question updateQuestion(Question question) {
        return questionRepository.save(question);
    }

    public String getQuestionTypeById(Long questionId) {
        return questionRepository.findQuestionTypeById(questionId);
    }

    public String getCorrectAnswerById(Long questionId) {
        return questionRepository.findCorrectAnswerById(questionId);
    }

    public Boolean getCorrectBooleanById(Long questionId) {
        return questionRepository.findCorrectBooleanById(questionId);
    }
}
