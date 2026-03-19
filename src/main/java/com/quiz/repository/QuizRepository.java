package com.quiz.repository;

import com.quiz.model.Quiz;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
    Repository interface for Quiz entity
*/
@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByIsActiveTrue();

    List<Quiz> findByCreatedById(Long adminId);
}
