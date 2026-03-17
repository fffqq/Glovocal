package com.example.demo.studentas;

import com.example.demo.studentas.Restaurants.RestaurantRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageControll {
    private final RestaurantRepo repo;
    private final CustServ custServ;
    public PageControll(RestaurantRepo repo,CustServ custServ){
        this.repo=repo;
        this.custServ=custServ;
    }

    @GetMapping("/Registration")
    public String registrationPage() {
        return "Registration";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "Reg-Autorize";
    }

    @GetMapping("/Restaurants")
    public String restaurantsPage(Model model) {
            model.addAttribute("allRestaurants", repo.findAll());
            return "Restaurants";
    }
}
