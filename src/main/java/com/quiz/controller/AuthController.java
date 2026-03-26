package com.quiz.controller;

import com.quiz.security.CustomUserDetailsService;
import com.quiz.model.Student;
import com.quiz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/*
    Controller for authentication and authorization
*/
@Controller
public class AuthController {

    private final CustomUserDetailsService userDetailsService;
    private final UserService userService;

    @Autowired
    public AuthController(CustomUserDetailsService userDetailsService, UserService userService) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("student", new Student());
        return "register";
    }

    @PostMapping("/register")
    public String registerStudent(@ModelAttribute("student") Student student) {
        if (userService.emailExists(student.getEmail())) {
            return "redirect:/register?error";
        }
        userService.registerStudent(student);
        return "redirect:/register?success";
    }

    @GetMapping("/default")
    public String defaultAfterLogin(Authentication authentication) {
        if (
            authentication
                .getAuthorities()
                .contains(new SimpleGrantedAuthority("ADMIN"))
        ) {
            return "redirect:/admin/dashboard";
        } else if (
            authentication
                .getAuthorities()
                .contains(new SimpleGrantedAuthority("STUDENT"))
        ) {
            return "redirect:/student/dashboard";
        }
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login?logout=true";
    }

    @GetMapping("/access-denied")
    public String accessDenied(Model model) {
        model.addAttribute(
            "error",
            "You don't have permission to access this page."
        );
        return "error";
    }
}
