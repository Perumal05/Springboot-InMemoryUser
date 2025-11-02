package com.zuna.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping
public class HomeController {

    @GetMapping("/home")
    @ResponseBody
    public String home(){
        return "Welcome to the Home Page!";
    }

    @GetMapping("/dashboard")
    @ResponseBody
    public String loginPage() {
        return "Login Successful!";
    }
}
