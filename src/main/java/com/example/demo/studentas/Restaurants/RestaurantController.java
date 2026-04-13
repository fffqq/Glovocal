package com.example.demo.studentas.Restaurants;

import com.example.demo.studentas.MenuItems.MenuRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping({"/Restaurants"})
public class RestaurantController {
    private final RestaurantRepo repo;
    private final MenuRepo Mrepo;
    public RestaurantController(RestaurantRepo repo,MenuRepo Mrepo){
        this.repo=repo;
        this.Mrepo=Mrepo;
    }
    @GetMapping("/Restaurants")
    public String restaurantsPage(Model model) {
        model.addAttribute("allRestaurants", repo.findAll());
        return "Restaurants";
    }
    @GetMapping({"/Restaurants/{RestaurantsId}"})
    public String RestaurantSample(@PathVariable("RestaurantsId")Long id,Model model){
        var res=repo.findById(id).orElse(null);
        var menu=Mrepo.findAllById(id);
        model.addAttribute("RestaurantByID",res);
        model.addAttribute("menuItems", menu);
        return "RestaurantSample";
    }





}
