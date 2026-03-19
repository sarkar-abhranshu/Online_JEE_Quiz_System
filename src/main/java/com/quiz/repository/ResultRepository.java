package com.quiz.repository;

import com.quiz.model.Result;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
    Repository interface for Result entity
*/
@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {
    List<Result> findByStudentIdOrderBySubmittedAtDesc(Long studentId);

    List<Result> findByQuizId(Long quizId);
}
