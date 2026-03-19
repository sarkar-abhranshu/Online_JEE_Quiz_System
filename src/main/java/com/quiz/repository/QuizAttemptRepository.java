package com.quiz.repository;

import com.quiz.model.QuizAttempt;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
    Repository interface for QuizAttempt entity
*/
@Repository
public interface QuizAttemptRepository
    extends JpaRepository<QuizAttempt, Long>
{
    List<QuizAttempt> findByStudentIdAndIsCompletedFalse(Long studentId);

    Optional<QuizAttempt> findByStudentIdAndQuizIdAndIsCompletedFalse(
        Long studentId,
        Long quizId
    );
}
