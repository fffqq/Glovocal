package com.example.demo.studentas;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageControll {

    @GetMapping("/Registration")
    public String registrationPage() {
        return "Registration";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "Reg-Autorize";
    }

    @GetMapping("/Restaurants")
    public String restaurantsPage() {
        return "Restaurants";
    }
}