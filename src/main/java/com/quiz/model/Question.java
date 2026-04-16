package com.quiz.model;

import com.quiz.strategy.EvaluationStrategy;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.springframework.cglib.core.Local;

/*
    SOLID/GRASP highlights:
    - OCP (Open/Closed Principle): New question types are added by subclassing without changing this base contract.
    - LSP (Liskov Substitution): Concrete questions are substitutable through the Question abstraction.
    - DIP (Dependency Inversion): Clients can depend on Question and EvaluationStrategy abstractions.
    - GRASP Polymorphism: Subclasses provide type-specific answer evaluation.
*/
@Entity
@Table(name = "questions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name = "question_type",
    discriminatorType = DiscriminatorType.STRING
)
public abstract class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Column(name = "question_text", columnDefinition = "TEXT", nullable = false)
    private String questionText;

    @Column(nullable = false)
    private Integer marks = 1;

    @Column(name = "question_type", insertable = false, updatable = false)
    private String questionType;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Question() {
        this.createdAt = LocalDateTime.now();
    }

    public Question(String questionText, Integer marks, Quiz quiz) {
        this.questionText = questionText;
        this.marks = marks;
        this.quiz = quiz;
        this.createdAt = LocalDateTime.now();
    }

    // Abstract method for polymorphic evaluation
    // Each subclass implements its own evaluation logic
    public abstract boolean evaluateAnswer(String studentAnswer);

    // Abstract method to get evaluation strategy
    public abstract EvaluationStrategy getEvaluationStrategy();

    public abstract String getCorrectAnswerForDisplay();

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public Integer getMarks() {
        return marks;
    }

    public void setMarks(Integer marks) {
        this.marks = marks;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
