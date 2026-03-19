package com.quiz.controller;

import com.quiz.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/*
    Controller for authentication and authorization
*/
@Controller
public class AuthController {

    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public AuthController(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
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
