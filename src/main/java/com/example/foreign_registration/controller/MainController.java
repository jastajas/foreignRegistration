package com.example.foreign_registration.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String redirecting() {
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String showMainPage(){
        return "index";
    }

}
