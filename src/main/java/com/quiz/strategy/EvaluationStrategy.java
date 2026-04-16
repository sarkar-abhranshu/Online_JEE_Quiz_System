package com.quiz.strategy;

import com.quiz.model.Question;

/*
    SOLID/GRASP highlights:
    - OCP (Open/Closed Principle): New evaluation algorithms are added as new strategy implementations.
    - DIP (Dependency Inversion): Clients can depend on this abstraction instead of concrete evaluators.
    - ISP (Interface Segregation): Provides a focused contract containing only evaluation behavior.
    - GRASP Polymorphism: Different strategies provide behavior through the same interface.
*/

public interface EvaluationStrategy {
    /*
	    Evaluates a student's answer against the correct answer.
	*/
    boolean evaluate(Question question, String studentAnswer);
}
