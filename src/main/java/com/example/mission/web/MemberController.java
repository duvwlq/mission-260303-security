package com.example.mission.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/list")
    public String list() {
        return "member/list";
    }

    @GetMapping("/detail")
    public String detail() {
        return "member/detail";
    }
}