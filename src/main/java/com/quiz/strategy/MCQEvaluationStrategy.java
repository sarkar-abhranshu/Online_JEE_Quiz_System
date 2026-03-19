package com.quiz.strategy;

import com.quiz.model.MCQQuestion;
import com.quiz.model.Question;

/*
    Concrete Strategy for evaluating MCQ questions.
*/
public class MCQEvaluationStrategy implements EvaluationStrategy {

    @Override
    public boolean evaluate(Question question, String studentAnswer) {
        if (!(question instanceof MCQQuestion)) {
            throw new IllegalArgumentException(
                "Question must be of type MCQQuestion"
            );
        }

        MCQQuestion mcq = (MCQQuestion) question;

        if (studentAnswer == null || mcq.getCorrectAnswer() == null) {
            return false;
        }
        return mcq
            .getCorrectAnswer()
            .trim()
            .equalsIgnoreCase(studentAnswer.trim());
    }
}
