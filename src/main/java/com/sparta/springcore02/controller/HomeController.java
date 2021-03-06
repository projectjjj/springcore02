package com.sparta.springcore02.controller;

import com.sparta.springcore02.model.User;
import com.sparta.springcore02.security.UserDetailsImpl;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        model.addAttribute("username", userDetails.getUsername());
        return "index";
        //원래는 home 콘트롤러가 없어도 자동으로 index를 내려주는데 이번에는 로그인해서 id값을 내려줘야해서 콘트롤러를 만듬.
    }

    @Secured("ROLE_ADMIN") //관리자만 이 주소를 사용할 수 있다고 알려줌.
    @GetMapping("/admin")
    public String admin(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        model.addAttribute("admin",true);
        return "index";
    }
}