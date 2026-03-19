package com.quiz.repository;

import com.quiz.model.Question;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
    Repository interface for Question entity.
*/
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByQuizId(Long quizId);
}
