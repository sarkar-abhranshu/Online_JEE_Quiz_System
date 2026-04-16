package com.quiz.model;

import jakarta.persistence.*;
import java.lang.reflect.Method;

/*
    SOLID/GRASP highlights:
    - SRP (Single Responsibility): Encapsulates answer state for one attempted question.
    - GRASP Information Expert: Stores correctness/marks data close to the answer entity that owns it.
*/
@Entity
@Table(name = "answers")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attempt_id", nullable = false)
    private QuizAttempt attempt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "student_answer", columnDefinition = "TEXT")
    private String studentAnswer;

    @Column(name = "is_correct")
    private Boolean isCorrect = false;

    @Column(name = "marks_obtained")
    private Integer marksObtained = 0;

    public Answer() {}

    public Answer(
        QuizAttempt attempt,
        Question question,
        String studentAnswer
    ) {
        this.attempt = attempt;
        this.question = question;
        this.studentAnswer = studentAnswer;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuizAttempt getAttempt() {
        return attempt;
    }

    public void setAttempt(QuizAttempt attempt) {
        this.attempt = attempt;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getStudentAnswer() {
        return studentAnswer;
    }

    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Integer getMarksObtained() {
        return marksObtained;
    }

    public void setMarksObtained(Integer marksObtained) {
        this.marksObtained = marksObtained;
    }

    @Transient
    public String getCorrectAnswerForDisplay() {
        if (question == null) {
            return "";
        }

        try {
            Method questionTypeMethod = question.getClass().getMethod("getQuestionType");
            String questionType = (String) questionTypeMethod.invoke(question);

            if ("MCQ".equals(questionType)) {
                Method correctAnswerMethod = question.getClass().getMethod("getCorrectAnswer");
                Object value = correctAnswerMethod.invoke(question);
                return value == null ? "" : String.valueOf(value);
            }

            if ("TRUE_FALSE".equals(questionType)) {
                Method correctBooleanMethod = question.getClass().getMethod("getCorrectBoolean");
                Object value = correctBooleanMethod.invoke(question);
                if (value instanceof Boolean boolValue) {
                    return boolValue ? "True" : "False";
                }
            }
        } catch (Exception ignored) {}

        return "";
    }
}
