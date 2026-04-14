package com.quiz.controller;

import com.quiz.model.*;
import com.quiz.security.CustomUserDetailsService;
import com.quiz.service.QuestionService;
import com.quiz.service.QuizService;
import com.quiz.service.ResultService;
import com.quiz.singleton.QuizSessionManager;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/*
    Controller for Student operations
*/
@Controller
@RequestMapping("/student")
public class StudentController {

    private final QuizService quizService;
    private final QuestionService questionService;
    private final ResultService resultService;
    private final CustomUserDetailsService userDetailsService;
    private final QuizSessionManager sessionManager =
        QuizSessionManager.getInstance();

    @Autowired
    public StudentController(
        QuizService quizService,
        QuestionService questionService,
        ResultService resultService,
        CustomUserDetailsService userDetailsService
    ) {
        this.quizService = quizService;
        this.questionService = questionService;
        this.resultService = resultService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        User student = userDetailsService.getUserByEmail(
            authentication.getName()
        );
        List<Result> results = resultService.getStudentResults(student.getId());
        List<Result> recentResults = results.stream().limit(5).toList();
        
        // Calculate average percentage
        double avgPercentage = recentResults.isEmpty() ? 0.0 : 
            recentResults.stream()
                .mapToDouble(Result::getPercentage)
                .average()
                .orElse(0.0);

        model.addAttribute("student", student);
        model.addAttribute("recentResults", recentResults);
        model.addAttribute("avgPercentage", avgPercentage);
        return "student/dashboard";
    }

    @GetMapping("/quizzes")
    public String listQuizzes(Model model) {
        List<Quiz> quizzes = quizService.getAllActiveQuizzes();
        model.addAttribute("quizzes", quizzes);
        return "student/quiz-list";
    }

    @GetMapping("/attempt/{quizId}")
    public String attemptQuiz(
        @PathVariable Long quizId,
        Authentication authentication,
        Model model,
        RedirectAttributes redirectAttributes
    ) {
        User student = userDetailsService.getUserByEmail(
            authentication.getName()
        );
        Quiz quiz = quizService.getQuizById(quizId);

        if (!quiz.getIsActive()) {
            redirectAttributes.addFlashAttribute(
                "error",
                "This quiz is not available."
            );
            return "redirect:/student/quizzes";
        }

        // Start quiz attempt using Singleton Session Manager
        QuizAttempt attempt = resultService.startQuizAttempt(student, quiz);
        sessionManager.startSession(student.getId(), quizId);

        List<Question> questions = questionService.getQuestionsByQuizId(quizId);
        Collections.shuffle(questions);

        model.addAttribute("quiz", quiz);
        model.addAttribute("questions", questions);
        model.addAttribute("attemptId", attempt.getId());
        model.addAttribute("durationMinutes", quiz.getDurationMinutes());

        return "student/attempt-quiz";
    }

    @PostMapping("/submit/{attemptId}")
    public String submitQuiz(
        @PathVariable Long attemptId,
        @RequestParam Map<String, String> allParams,
        Authentication authentication,
        RedirectAttributes redirectAttributes
    ) {
        User student = userDetailsService.getUserByEmail(
            authentication.getName()
        );
        QuizAttempt attempt = resultService.getAttemptById(attemptId);

        // Check if session has expired using Singleton
        if (
            sessionManager.hasSessionExpired(
                student.getId(),
                attempt.getQuiz().getId(),
                attempt.getQuiz().getDurationMinutes()
            )
        ) {
            redirectAttributes.addFlashAttribute("error", "Quiz time expired!");
        }

        // End session
        sessionManager.endSession(student.getId(), attempt.getQuiz().getId());

        // Extract answers from parameters
        Map<Long, String> answers = new HashMap<>();
        for (Map.Entry<String, String> entry : allParams.entrySet()) {
            if (entry.getKey().startsWith("answer_")) {
                Long questionId = Long.parseLong(
                    entry.getKey().replace("answer_", "")
                );
                answers.put(questionId, entry.getValue());
            }
        }

        // Submit and evaluate quiz
        Result result;
        try {
            result = resultService.submitQuiz(attemptId, answers);
        } catch (RuntimeException e) {
            if ("Quiz already submitted".equals(e.getMessage())) {
                Result existingResult = resultService.getResultByAttemptId(attemptId);
                redirectAttributes.addFlashAttribute(
                    "error",
                    "This quiz was already submitted. Showing your result."
                );
                return "redirect:/student/result/" + existingResult.getId();
            }
            throw e;
        }

        return "redirect:/student/result/" + result.getId();
    }

    @GetMapping("/result/{resultId}")
    public String viewResult(@PathVariable Long resultId, Model model) {
        Result result = resultService.getResultById(resultId);
        List<Answer> answers = resultService.getAnswersByAttemptId(
            result.getAttempt().getId()
        );
        Map<Long, String> correctAnswerMap = new HashMap<>();

        for (Answer answer : answers) {
            Question question = answer.getQuestion();
            Long questionId = question.getId();
            String questionType = questionService.getQuestionTypeById(questionId);

            if ("MCQ".equals(questionType)) {
                String correctAnswer = questionService.getCorrectAnswerById(questionId);
                correctAnswerMap.put(
                    questionId,
                    correctAnswer != null ? correctAnswer : ""
                );
            } else if ("TRUE_FALSE".equals(questionType)) {
                Boolean correctBoolean = questionService.getCorrectBooleanById(
                    questionId
                );
                correctAnswerMap.put(
                    questionId,
                    (correctBoolean != null && correctBoolean) ? "True" : "False"
                );
            } else {
                correctAnswerMap.put(questionId, "");
            }
        }

        model.addAttribute("result", result);
        model.addAttribute("answers", answers);
        model.addAttribute("correctAnswerMap", correctAnswerMap);
        return "student/result";
    }

    @GetMapping("/history")
    public String viewHistory(Authentication authentication, Model model) {
        User student = userDetailsService.getUserByEmail(
            authentication.getName()
        );
        List<Result> results = resultService.getStudentResults(student.getId());

        model.addAttribute("results", results);
        return "student/history";
    }
}
