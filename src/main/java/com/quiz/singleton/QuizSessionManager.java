package com.quiz.singleton;

import com.quiz.model.Quiz;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.cglib.core.Local;

/*
    Singleton Pattern implementation for managing quiz sessions.
    Ensures only 1 instance of session manager exists throughout the app.
*/
public class QuizSessionManager {

    // Private static instance (eager init for thread safety)
    private static final QuizSessionManager INSTANCE = new QuizSessionManager();

    // Store active quiz sessions: Key = "userId_quizId", Value = StartTime
    private final Map<String, LocalDateTime> activeSessions;

    // Private constructor prevents instantiation from outside
    private QuizSessionManager() {
        this.activeSessions = new ConcurrentHashMap<>();
    }

    /*
	    Returns the singleton instance.
	*/
    public static QuizSessionManager getInstance() {
        return INSTANCE;
    }

    /*
        Starts a quiz session for a user.
    */
    public void startSession(Long userId, Long quizId) {
        String sessionKey = generateSessionKey(userId, quizId);
        activeSessions.put(sessionKey, LocalDateTime.now());
    }

    /*
        Ends a quiz session for a user.
    */
    public void endSession(Long userId, Long quizId) {
        String sessionKey = generateSessionKey(userId, quizId);
        activeSessions.remove(sessionKey);
    }

    /*
        Checks if a session is active.
    */
    public boolean isSessionActive(Long userId, Long quizId) {
        String sessionKey = generateSessionKey(userId, quizId);
        return activeSessions.containsKey(sessionKey);
    }

    /*
        Gets session start time
    */
    public LocalDateTime getSessionStartTime(Long userId, Long quizId) {
        String sessionKey = generateSessionKey(userId, quizId);
        return activeSessions.get(sessionKey);
    }

    /*
        Validates if session has expired based on duration
    */
    public boolean hasSessionExpired(
        Long userId,
        Long quizId,
        int durationMinutes
    ) {
        LocalDateTime startTime = getSessionStartTime(userId, quizId);
        if (startTime == null) {
            return true;
        }

        LocalDateTime expiryTime = startTime.plusMinutes(durationMinutes);
        return LocalDateTime.now().isAfter(expiryTime);
    }

    /*
        Generates a unique session key
    */
    private String generateSessionKey(Long userId, Long quizId) {
        return userId + "_" + quizId;
    }
}
