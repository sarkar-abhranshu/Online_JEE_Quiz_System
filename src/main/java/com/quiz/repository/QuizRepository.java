package com.quiz.repository;

import com.quiz.model.Quiz;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
    SOLID/GRASP highlights:
    - DIP (Dependency Inversion): Service layer depends on repository abstraction rather than implementation.
    - GRASP Pure Fabrication: Encapsulates persistence queries as a dedicated fabricated component.
*/
@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByIsActiveTrue();

    List<Quiz> findByCreatedById(Long adminId);
}
