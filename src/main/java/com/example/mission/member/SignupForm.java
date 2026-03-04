package com.example.mission.member;

public record SignupForm(
        String username,
        String password,
        String passwordConfirm
) {}