package com.quiz.repository;

import com.quiz.model.Answer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
    Repository interface for Answer entity
*/
@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByAttemptId(Long attemptId);
}
