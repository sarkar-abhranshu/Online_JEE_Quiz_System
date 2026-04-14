package com.quiz.model;

import com.quiz.strategy.EvaluationStrategy;
import com.quiz.strategy.MCQEvaluationStrategy;
import jakarta.persistence.*;

/*
    MCQ implementation
*/
@Entity
@DiscriminatorValue("MCQ")
public class MCQQuestion extends Question {

    @Column(name = "option_a", length = 500)
    private String optionA;

    @Column(name = "option_b", length = 500)
    private String optionB;

    @Column(name = "option_c", length = 500)
    private String optionC;

    @Column(name = "option_d", length = 500)
    private String optionD;

    @Column(name = "correct_answer", length = 500)
    private String correctAnswer;

    @Transient
    private EvaluationStrategy evaluationStrategy = new MCQEvaluationStrategy();

    public MCQQuestion() {
        super();
    }

    public MCQQuestion(
        String questionText,
        Integer marks,
        Quiz quiz,
        String optionA,
        String optionB,
        String optionC,
        String optionD,
        String correctAnswer
    ) {
        super(questionText, marks, quiz);
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean evaluateAnswer(String studentAnswer) {
        if (studentAnswer == null || correctAnswer == null) {
            return false;
        }
        return correctAnswer.trim().equalsIgnoreCase(studentAnswer.trim());
    }

    @Override
    public EvaluationStrategy getEvaluationStrategy() {
        return evaluationStrategy;
    }

    @Override
    public String getCorrectAnswerForDisplay() {
        return correctAnswer;
    }

    // Getters and Setters
    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public void setEvaluationStrategy(EvaluationStrategy evaluationStrategy) {
        this.evaluationStrategy = evaluationStrategy;
    }
}
