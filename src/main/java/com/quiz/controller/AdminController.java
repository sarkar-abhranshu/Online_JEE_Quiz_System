package com.quiz.controller;

import com.quiz.factory.QuestionFactory;
import com.quiz.model.*;
import com.quiz.security.CustomUserDetailsService;
import com.quiz.service.QuestionService;
import com.quiz.service.QuizService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/*
    Controller for Admin operations
*/
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final QuizService quizService;
    private final QuestionService questionService;
    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public AdminController(
        QuizService quizService,
        QuestionService questionService,
        CustomUserDetailsService userDetailsService
    ) {
        this.quizService = quizService;
        this.questionService = questionService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        User admin = userDetailsService.getUserByEmail(
            authentication.getName()
        );
        List<Quiz> quizzes = quizService.getQuizzesByAdmin(admin.getId());

        model.addAttribute("admin", admin);
        model.addAttribute("quizzes", quizzes);
        return "admin/dashboard";
    }

    @GetMapping("/create-quiz")
    public String showCreateQuizForm(Model model) {
        model.addAttribute("quiz", new Quiz());
        return "admin/create-quiz";
    }

    @PostMapping("/create-quiz")
    public String createQuiz(
        @ModelAttribute Quiz quiz,
        Authentication authentication,
        RedirectAttributes redirectAttributes
    ) {
        if (quiz.getDurationMinutes() == null || quiz.getDurationMinutes() < 1) {
            redirectAttributes.addFlashAttribute(
                "error",
                "Please provide a valid quiz duration in minutes."
            );
            return "redirect:/admin/create-quiz";
        }

        User admin = userDetailsService.getUserByEmail(
            authentication.getName()
        );
        quiz.setCreatedBy(admin);

        Quiz savedQuiz;
        try {
            savedQuiz = quizService.createQuiz(quiz);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                "error",
                "Could not create quiz. Please check all fields and try again."
            );
            return "redirect:/admin/create-quiz";
        }

        redirectAttributes.addFlashAttribute(
            "success",
            "Quiz created successfully!"
        );
        return "redirect:/admin/add-question/" + savedQuiz.getId();
    }

    @GetMapping("/add-question/{quizId}")
    public String showAddQuestionForm(@PathVariable Long quizId, Model model) {
        Quiz quiz = quizService.getQuizById(quizId);
        List<Question> questions = questionService.getQuestionsByQuizId(quizId);

        model.addAttribute("quiz", quiz);
        model.addAttribute("questions", questions);
        return "admin/add-question";
    }

    @PostMapping("/add-question/{quizId}")
    public String addQuestion(
        @PathVariable Long quizId,
        @RequestParam String questionType,
        @RequestParam String questionText,
        @RequestParam Integer marks,
        @RequestParam(required = false) String optionA,
        @RequestParam(required = false) String optionB,
        @RequestParam(required = false) String optionC,
        @RequestParam(required = false) String optionD,
        @RequestParam(required = false) String correctAnswer,
        @RequestParam(required = false) String correctBoolean,
        @RequestParam(required = false) String keywordAnswer,
        RedirectAttributes redirectAttributes
    ) {
        Quiz quiz = quizService.getQuizById(quizId);
        Question question = null;

        try {
            // Using Factory pattern to create questions
            switch (questionType) {
                case "MCQ":
                    question = QuestionFactory.createQuestion(
                        "MCQ",
                        questionText,
                        marks,
                        quiz,
                        optionA,
                        optionB,
                        optionC,
                        optionD,
                        correctAnswer
                    );
                    break;
                case "TRUE_FALSE":
                    Boolean boolValue = Boolean.parseBoolean(correctBoolean);
                    question = QuestionFactory.createQuestion(
                        "TRUE_FALSE",
                        questionText,
                        marks,
                        quiz,
                        boolValue
                    );
                    break;
            }

            if (question != null) {
                questionService.addQuestion(question);
                redirectAttributes.addFlashAttribute(
                    "success",
                    "Question added successfully!"
                );
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                "error",
                "Error adding question: " + e.getMessage()
            );
        }

        return "redirect:/admin/add-question/" + quizId;
    }

    @GetMapping("/analytics/{quizId}")
    public String showAnalytics(@PathVariable Long quizId, Model model) {
        Map<String, Object> analytics = quizService.getQuizAnalytics(quizId);

        model.addAttribute("quiz", analytics.get("quiz"));
        model.addAttribute("totalAttempts", analytics.get("totalAttempts"));
        model.addAttribute("averageScore", analytics.get("averageScore"));
        model.addAttribute(
            "averagePercentage",
            analytics.get("averagePercentage")
        );
        model.addAttribute("results", analytics.get("results"));

        return "admin/analytics";
    }

    @PostMapping("/toggle-quiz/{quizId}")
    public String toggleQuizStatus(
        @PathVariable Long quizId,
        RedirectAttributes redirectAttributes
    ) {
        Quiz quiz = quizService.getQuizById(quizId);
        quiz.setIsActive(!quiz.getIsActive());
        quizService.updateQuiz(quiz);

        String status = quiz.getIsActive() ? "activated" : "deactivated";
        redirectAttributes.addFlashAttribute(
            "success",
            "Quiz " + status + " successfully!"
        );

        return "redirect:/admin/dashboard";
    }

    @PostMapping("/delete-question/{questionId}")
    public String deleteQuestion(
        @PathVariable Long questionId,
        @RequestParam Long quizId,
        RedirectAttributes redirectAttributes
    ) {
        questionService.deleteQuestion(questionId);
        redirectAttributes.addFlashAttribute(
            "success",
            "Question deleted successfully!"
        );
        return "redirect:/admin/add-question/" + quizId;
    }
}
