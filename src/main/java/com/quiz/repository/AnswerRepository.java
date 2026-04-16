package com.quiz.repository;

import com.quiz.model.Answer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
    SOLID/GRASP highlights:
    - DIP (Dependency Inversion): Higher layers depend on this repository abstraction, not persistence implementation details.
    - GRASP Pure Fabrication: Repository isolates data access concerns outside domain entities/services.
*/
@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByAttemptId(Long attemptId);
}
