package com.quiz.repository;

import com.quiz.model.Result;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
    SOLID/GRASP highlights:
    - DIP (Dependency Inversion): Services rely on this repository abstraction to access Result data.
    - GRASP Pure Fabrication: Persistence responsibility is intentionally separated from domain objects.
*/
@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {
    List<Result> findByStudentIdOrderBySubmittedAtDesc(Long studentId);

    List<Result> findByQuizId(Long quizId);

    Optional<Result> findByAttemptId(Long attemptId);
}
