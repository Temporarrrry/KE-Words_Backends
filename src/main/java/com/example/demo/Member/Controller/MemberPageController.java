package com.example.demo.Member.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberPageController {
    @GetMapping(value = "/member/registerPage")
    public String register() {
        return "member/register";
    }

    @GetMapping(value = "/member/login")
    public String login() { return "member/login"; }
}
