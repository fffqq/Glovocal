package com.example.demo.studentas.Admins;

import com.example.demo.studentas.CustServ;
import com.example.demo.studentas.Customer;
import com.example.demo.studentas.CustomerRepo;
import com.example.demo.studentas.Restaurants.RestaurantRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminControl {
    private final CustomerRepo repo;
    private final CustServ custServ;
    public AdminControl(CustomerRepo repo,CustServ custServ){
        this.repo=repo;
        this.custServ=custServ;
    }
    @GetMapping("/Admins")
    public String restaurantsPage(Model model) {
        model.addAttribute("allCustomers", repo.findAll());
        return "Admins";
    }
    @PostMapping("/Admins/create")
    public String createUser(@ModelAttribute Customer customer) {
        custServ.PostCust(customer);
        return "redirect:/Admins";
    }
    @PostMapping("/Admins/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        custServ.deleteCust(id);
        return "redirect:/Admins";
    }
    @PostMapping("/Admins/update-role/{id}")
    public String updateRole(@PathVariable Long id, @RequestParam String role) {
        Customer customer = repo.findById(id).orElseThrow();
        customer.setRole(role);
        repo.save(customer);
        return "redirect:/Admins";
    }
    @GetMapping("/Admins/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Customer customer = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", customer);
        return "edit-user";
    }
    @PostMapping("/Admins/update/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute Customer customer) {
        Customer exist=repo.findById(id).orElseThrow();
        exist.setLogin(customer.getLogin());
        exist.setEmail(customer.getEmail());
        exist.setRole(customer.getRole());


        repo.save(exist);
        return "redirect:/Admins";
    }
}
