package com.quiz.service;

import com.quiz.model.Quiz;
import com.quiz.model.Result;
import com.quiz.repository.QuizRepository;
import com.quiz.repository.ResultRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
    SOLID/GRASP highlights:
    - SRP (Single Responsibility): Encapsulates quiz lifecycle and analytics use-cases.
    - DIP (Dependency Inversion): Depends on QuizRepository/ResultRepository abstractions.
    - GRASP Indirection: Keeps controllers decoupled from persistence/query details.
*/
@Service
@Transactional
public class QuizService {

    private final QuizRepository quizRepository;
    private final ResultRepository resultRepository;

    @Autowired
    public QuizService(
        QuizRepository quizRepository,
        ResultRepository resultRepository
    ) {
        this.quizRepository = quizRepository;
        this.resultRepository = resultRepository;
    }

    public Quiz createQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public Quiz getQuizById(Long id) {
        return quizRepository
            .findById(id)
            .orElseThrow(() ->
                new RuntimeException("Quiz not found with id: " + id)
            );
    }

    public List<Quiz> getAllActiveQuizzes() {
        return quizRepository.findByIsActiveTrue();
    }

    public List<Quiz> getQuizzesByAdmin(Long adminId) {
        return quizRepository.findByCreatedById(adminId);
    }

    public Quiz updateQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public void deleteQuiz(Long id) {
        quizRepository.deleteById(id);
    }

    public Map<String, Object> getQuizAnalytics(Long quizId) {
        Quiz quiz = getQuizById(quizId);
        List<Result> results = resultRepository.findByQuizId(quizId);

        Map<String, Object> analytics = new HashMap<>();
        analytics.put("quiz", quiz);
        analytics.put("totalAttempts", results.size());

        if (!(results.isEmpty())) {
            double avgScore = results
                .stream()
                .mapToDouble(Result::getScore)
                .average()
                .orElse(0.0);

            double avgPercentage = results
                .stream()
                .mapToDouble(Result::getPercentage)
                .average()
                .orElse(0.0);

            analytics.put("averageScore", avgScore);
            analytics.put("averagePercentage", avgPercentage);
        } else {
            analytics.put("averageScore", 0.0);
            analytics.put("averagePercentage", 0.0);
        }

        analytics.put("results", results);
        return analytics;
    }
}
