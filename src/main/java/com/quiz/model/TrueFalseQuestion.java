package com.quiz.model;

import com.quiz.strategy.EvaluationStrategy;
import com.quiz.strategy.TrueFalseEvaluationStrategy;
import jakarta.persistence.*;

/*
    True/False Question implementation
*/
@Entity
@DiscriminatorValue("TRUE_FALSE")
public class TrueFalseQuestion extends Question {

    @Column(name = "correct_boolean")
    private Boolean correctBoolean;

    @Transient
    private EvaluationStrategy evaluationStrategy =
        new TrueFalseEvaluationStrategy();

    public TrueFalseQuestion() {
        super();
    }

    public TrueFalseQuestion(
        String questionText,
        Integer marks,
        Quiz quiz,
        Boolean correctBoolean
    ) {
        super(questionText, marks, quiz);
        this.correctBoolean = correctBoolean;
    }

    @Override
    public boolean evaluateAnswer(String studentAnswer) {
        if (studentAnswer == null || correctBoolean == null) {
            return false;
        }

        studentAnswer = studentAnswer.trim().toLowerCase();

        if (correctBoolean) {
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

    @Override
    public EvaluationStrategy getEvaluationStrategy() {
        return evaluationStrategy;
    }

    // Getters and Setters
    public Boolean getCorrectBoolean() {
        return correctBoolean;
    }

    public void setCorrectBoolean(Boolean correctBoolean) {
        this.correctBoolean = correctBoolean;
    }

    public void setEvaluationStrategy(EvaluationStrategy evaluationStrategy) {
        this.evaluationStrategy = evaluationStrategy;
    }
}
