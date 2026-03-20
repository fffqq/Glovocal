package com.example.demo.studentas.Restaurants;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping({"/Restaurants"})
public class RestaurantController {
    private final RestaurantRepo repo;
    public RestaurantController(RestaurantRepo repo){
        this.repo=repo;
    }


    @GetMapping({""})
    public String RestaurantSample(){

        return "redirect:/RestaurantSample";
    }





}
