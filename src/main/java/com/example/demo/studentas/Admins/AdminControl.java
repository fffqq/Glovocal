package com.example.demo.studentas.Admins;

import com.example.demo.studentas.CustServ;
import com.example.demo.studentas.Customer;
import com.example.demo.studentas.CustomerRepo;
import com.example.demo.studentas.MenuItems.Menu;
import com.example.demo.studentas.MenuItems.MenuRepo;
import com.example.demo.studentas.Restaurants.RestaurantEntity;
import com.example.demo.studentas.Restaurants.RestaurantInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminControl {
    private final CustomerRepo repo;
    private final CustServ custServ;
    private final RestaurantInterface restaurantInterface;
    private final MenuRepo Mrepo;
    @Autowired
    private final AdminServices adminServices;
    public AdminControl(CustomerRepo repo,CustServ custServ,RestaurantInterface restaurantInterface,MenuRepo Mrepo,AdminServices adminServices){
        this.repo=repo;
        this.custServ=custServ;
        this.restaurantInterface=restaurantInterface;
        this.Mrepo=Mrepo;
        this.adminServices=adminServices;
    }
    @GetMapping("/Admins")
    public String restaurantsPage(Model model) {
        model.addAttribute("allCustomers", repo.findAll());
        model.addAttribute("allRestaurants",restaurantInterface.findAll());
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

    @PostMapping("/Admins/restaurants/create")
    public String createRestaurant(@ModelAttribute RestaurantEntity restaurant) {
        restaurantInterface.create(restaurant);
        return "redirect:/Admins";
    }
    @PostMapping("/Admins/restaurants/delete/{id}")
    public String deleteRestaurant(@PathVariable Long id,@ModelAttribute RestaurantEntity restaurant) {
        restaurantInterface.deleteById(id);
        return "redirect:/Admins";
    }

    @GetMapping("/Admins/restaurants/edit/{id}")
    public String showEditFormRestaurants(@PathVariable Long id, Model model) {
        RestaurantEntity restaurant=restaurantInterface.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid restaurants Id:" + id));
        model.addAttribute("restaurant", restaurant);
        var menu=Mrepo.findAllByRestaurantId(id);
        model.addAttribute("menuItems", menu);
        return "edit-restaurant";
    }

    @PostMapping("/Admins/restaurants/edit/{resId}/delete/{itemId}")
    public String deleteMenuItem(@PathVariable Long resId, @PathVariable Long itemId) {
        Mrepo.deleteById(itemId);
        return "redirect:/Admins/restaurants/edit/" + resId;
    }

    @PostMapping("/Admins/restaurants/edit/{resId}/addItem")
    public String addMenuItem(@PathVariable Long resId,
                              @RequestParam String name,
                              @RequestParam Double price) {
        adminServices.createItem(name, price, resId);
        return "redirect:/Admins/restaurants/edit/" + resId;
    }

    @GetMapping("/Admins/restaurants/edit/{resId}/menu/edit/{itemId}")
    public String showEditMenuPage(@PathVariable Long resId, @PathVariable Long itemId, Model model) {
        Menu item = Mrepo.findById(itemId).orElseThrow();
        model.addAttribute("item", item);
        model.addAttribute("resId", resId); // Чтобы знать, куда вернуться
        return "edit-menu-item";
    }

    @PostMapping("/Admins/restaurants/edit/{resId}/menu/update/{itemId}")
    public String updateMenuItem(@PathVariable Long resId,
                                 @PathVariable Long itemId,
                                 @RequestParam String name,
                                 @RequestParam Double price) {
        adminServices.updateItem(itemId, name, price);
        return "redirect:/Admins/restaurants/edit/" + resId;
    }
}
