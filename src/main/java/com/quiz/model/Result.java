package com.quiz.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/*
    Represents result of quiz attempt
*/
@Entity
@Table(name = "results")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attempt_id", nullable = false)
    private QuizAttempt attempt;

    @Column(nullable = false)
    private Double score;

    @Column(name = "total_marks", nullable = false)
    private Integer totalMarks;

    @Column(nullable = false)
    private Double percentage;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    public Result() {
        this.submittedAt = LocalDateTime.now();
    }

    public Result(
        User student,
        Quiz quiz,
        QuizAttempt attempt,
        Double score,
        Integer totalMarks
    ) {
        this.student = student;
        this.quiz = quiz;
        this.attempt = attempt;
        this.score = score;
        this.totalMarks = totalMarks;
        this.percentage = (score / totalMarks) * 100.0;
        this.submittedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public QuizAttempt getAttempt() {
        return attempt;
    }

    public void setAttempt(QuizAttempt attempt) {
        this.attempt = attempt;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(Integer totalMarks) {
        this.totalMarks = totalMarks;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
}
