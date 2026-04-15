package com.quiz.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/*
    Quiz entity representing quiz in the system
*/
@Entity
@Table(name = "quizzes")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;

    @Column(name = "negative_marks", nullable = false)
    private Integer negativeMarks = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(
        mappedBy = "quiz",
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    private List<Question> questions = new ArrayList<>();

    @OneToMany(
        mappedBy = "quiz",
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    private List<Result> results = new ArrayList<>();

    public Quiz() {
        this.createdAt = LocalDateTime.now();
    }

    public Quiz(
        String title,
        String description,
        Integer durationMinutes,
        User createdBy
    ) {
        this.title = title;
        this.description = description;
        this.durationMinutes = durationMinutes;
        this.negativeMarks = 0;
        this.createdBy = createdBy;
        this.isActive = true;
        this.createdAt = LocalDateTime.now();
    }

    public int getTotalMarks() {
        return questions.stream().mapToInt(Question::getMarks).sum();
    }

    public String getNegativeMarkingDisplay() {
        if (negativeMarks == null || negativeMarks <= 0) {
            return "None";
        }
        return "-" + negativeMarks + " per wrong answer";
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public Integer getNegativeMarks() {
        return negativeMarks;
    }

    public void setNegativeMarks(Integer negativeMarks) {
        this.negativeMarks = negativeMarks;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}
