package com.example.mission.web;

import com.example.mission.member.SignupForm;
import com.example.mission.member.SignupService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SignupController {

    private final SignupService signupService;

    public SignupController(SignupService signupService) {
        this.signupService = signupService;
    }

    @GetMapping("/signup")
    public String signupForm() {
        return "member/signup";
    }

    @PostMapping("/signup")
    public String signup(@RequestParam String username,
                         @RequestParam String password,
                         @RequestParam String passwordConfirm,
                         Model model) {
        try {
            signupService.signup(new SignupForm(username, password, passwordConfirm));
            return "redirect:/login?signup";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("username", username);
            return "member/signup";
        }
    }
}