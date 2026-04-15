package com.quiz.service;

import com.quiz.model.*;
import com.quiz.repository.AnswerRepository;
import com.quiz.repository.QuizAttemptRepository;
import com.quiz.repository.ResultRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
    Service class for Result and quiz submission operations.
*/
@Service
@Transactional
public class ResultService {

    private final ResultRepository resultRepository;
    private final QuizAttemptRepository attemptRepository;
    private final AnswerRepository answerRepository;

    @Autowired
    public ResultService(
        ResultRepository resultRepository,
        QuizAttemptRepository attemptRepository,
        AnswerRepository answerRepository
    ) {
        this.resultRepository = resultRepository;
        this.attemptRepository = attemptRepository;
        this.answerRepository = answerRepository;
    }

    public QuizAttempt startQuizAttempt(User student, Quiz quiz) {
        QuizAttempt attempt = new QuizAttempt(student, quiz);
        return attemptRepository.save(attempt);
    }

    public QuizAttempt getAttemptById(Long attemptId) {
        return attemptRepository
            .findById(attemptId)
            .orElseThrow(() ->
                new RuntimeException("Attempt not found with id: " + attemptId)
            );
    }

    public Result submitQuiz(Long attemptId, Map<Long, String> answers) {
        QuizAttempt attempt = getAttemptById(attemptId);

        if (attempt.getIsCompleted()) {
            throw new RuntimeException("Quiz already submitted");
        }

        Quiz quiz = attempt.getQuiz();
        List<Question> questions = quiz.getQuestions();

        int totalScore = 0;
        int totalMarks = quiz.getTotalMarks();
        int negativeMarksPerWrong =
            quiz.getNegativeMarks() != null ? quiz.getNegativeMarks() : 0;

        // Evaluating each answer
        for (Question question : questions) {
            String studentAnswer = answers.get(question.getId());

            Answer answer = new Answer(attempt, question, studentAnswer);

            boolean isCorrect = question.evaluateAnswer(studentAnswer);
            answer.setIsCorrect(isCorrect);

            if (isCorrect) {
                answer.setMarksObtained(question.getMarks());
                totalScore += question.getMarks();
            } else {
                answer.setMarksObtained(-negativeMarksPerWrong);
                totalScore -= negativeMarksPerWrong;
            }

            answerRepository.save(answer);
        }

        // Marking attempt as completed
        attempt.setIsCompleted(true);
        attempt.setEndTime(LocalDateTime.now());
        attemptRepository.save(attempt);

        // Creating and saving result
        Result result = new Result(
            attempt.getStudent(),
            quiz,
            attempt,
            (double) totalScore,
            totalMarks
        );

        return resultRepository.save(result);
    }

    public List<Result> getStudentResults(Long studentId) {
        return resultRepository.findByStudentIdOrderBySubmittedAtDesc(
            studentId
        );
    }

    public Result getResultById(Long resultId) {
        return resultRepository
            .findById(resultId)
            .orElseThrow(() ->
                new RuntimeException("Result not found with id: " + resultId)
            );
    }

    public List<Result> getResultsByQuizId(Long quizId) {
        return resultRepository.findByQuizId(quizId);
    }

    public Result getResultByAttemptId(Long attemptId) {
        return resultRepository
            .findByAttemptId(attemptId)
            .orElseThrow(() ->
                new RuntimeException("Result not found for attempt id: " + attemptId)
            );
    }

    public List<Answer> getAnswersByAttemptId(Long attemptId) {
        return answerRepository.findByAttemptId(attemptId);
    }
}
