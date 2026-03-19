package com.quiz.factory;

import com.quiz.model.*;

/*
    Factory pattern implementation for creating question objects.
    Provides a centralized way to create different question types
    without exposing the instantiation logic to the client.
*/
public class QuestionFactory {

    /*
        Creates a Question object based on the type specified.

        @param type         The type of question (MCQ, TRUE_FALSE)
        @param questionText The text of the question
        @param marks        The marks allocated for the question
        @param quiz         Quiz the question belongs to
        @param params       Additional parameters specific to the question type
        @return Question object of the appropriate type
    */
    public static Question createQuestion(
        String type,
        String questionText,
        Integer marks,
        Quiz quiz,
        Object... params
    ) {
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException(
                "Question type cannot be null or empty"
            );
        }

        switch (type.toUpperCase()) {
            case "MCQ":
                if (params.length < 5) {
                    throw new IllegalArgumentException(
                        "MCQ requires 5 params: optionA, optionB, optionC, optionD, correctAnswer"
                    );
                }
                return new MCQQuestion(
                    questionText,
                    marks,
                    quiz,
                    (String) params[0], // optionA
                    (String) params[1], // optionB
                    (String) params[2], // optionC
                    (String) params[3], // optionD
                    (String) params[4]  // correctAnswer
                );
            case "TRUE_FALSE":
                if (params.length < 1) {
                    throw new IllegalArgumentException(
                        "True/False requires 1 parameter: correctBoolean"
                    );
                }
                return new TrueFalseQuestion(
                    questionText,
                    marks,
                    quiz,
                    (Boolean) params[0] // correctBoolean
                );
            default:
                throw new IllegalArgumentException(
                    "Unknown question type: " + type
                );
        }
    }
}
