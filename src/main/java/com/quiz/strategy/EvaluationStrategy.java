package com.quiz.strategy;

import com.quiz.model.Question;

/*
    Strategy pattern interface for question evaluation
    Different evaluation strategies can be implemented without modifying existing code.
*/

public interface EvaluationStrategy {
    /*
	    Evaluates a student's answer against the correct answer.
	*/
    boolean evaluate(Question question, String studentAnswer);
}
