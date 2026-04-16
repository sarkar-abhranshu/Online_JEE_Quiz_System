package com.quiz.strategy;

import com.quiz.model.Question;
import com.quiz.model.TrueFalseQuestion;

/*
    SOLID/GRASP highlights:
    - OCP (Open/Closed Principle): Adds True/False evaluation behavior via extension.
    - LSP (Liskov Substitution): Works wherever EvaluationStrategy is required.
    - GRASP Polymorphism: Implements evaluate(...) with True/False-specific rules.
*/
public class TrueFalseEvaluationStrategy implements EvaluationStrategy {

    @Override
    public boolean evaluate(Question question, String studentAnswer) {
        if (!(question instanceof TrueFalseQuestion)) {
            throw new IllegalArgumentException(
                "Question must be of type TrueFalseQuestion"
            );
        }

        TrueFalseQuestion tfq = (TrueFalseQuestion) question;

        if (studentAnswer == null || tfq.getCorrectBoolean() == null) {
            return false;
        }

        studentAnswer = studentAnswer.trim().toLowerCase();

        if (tfq.getCorrectBoolean()) {
            return (
                studentAnswer.equals("true") ||
                studentAnswer.equals("t") ||
                studentAnswer.equals("yes") ||
                studentAnswer.equals("1")
            );
        } else {
            return (
                studentAnswer.equals("false") ||
                studentAnswer.equals("f") ||
                studentAnswer.equals("no") ||
                studentAnswer.equals("0")
            );
        }
    }
}
