package com.quiz.repository;

import com.quiz.model.Question;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
    Repository interface for Question entity.
*/
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByQuizId(Long quizId);

    @Query(
        value = "SELECT question_type FROM questions WHERE id = :questionId",
        nativeQuery = true
    )
    String findQuestionTypeById(@Param("questionId") Long questionId);

    @Query(
        value = "SELECT correct_answer FROM questions WHERE id = :questionId",
        nativeQuery = true
    )
    String findCorrectAnswerById(@Param("questionId") Long questionId);

    @Query(
        value = "SELECT correct_boolean FROM questions WHERE id = :questionId",
        nativeQuery = true
    )
    Boolean findCorrectBooleanById(@Param("questionId") Long questionId);
}
