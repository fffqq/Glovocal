package com.example.demo.studentas.driver;

import com.example.demo.studentas.Order.OrderEntity;
import com.example.demo.studentas.Order.OrderRepo;
import com.example.demo.studentas.Order.OrderServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
@Controller
public class DriverControl {
    private final OrderRepo orderRepo;
    private final OrderServices orderServices;
    public DriverControl(OrderServices orderServices,OrderRepo orderRepo){
        this.orderServices=orderServices;
        this.orderRepo=orderRepo;
    }
    @GetMapping("/Drivers")
    public String showDriverPanel(@CookieValue("user_login") String login, Model model) {

        List<OrderEntity> allOrders = orderRepo.findAll();

        List<OrderEntity> availableOrders = new java.util.ArrayList<>();
        List<OrderEntity> myHistory = new java.util.ArrayList<>();

        Long currentDriverId = orderServices.getCustomerIdByLogin(login);


        for (OrderEntity o : allOrders) {

            if ("SENT".equals(o.getStatus()) && o.getDriverId() == null) {
                availableOrders.add(o);
            }
            else if ("DELIVERED".equals(o.getStatus()) && currentDriverId.equals(o.getDriverId())) {
                myHistory.add(o);
            }
        }

        model.addAttribute("available", availableOrders);
        model.addAttribute("history", myHistory);
        return "DriverPanel";
    }
    @GetMapping("/Drivers/accept/{id}")
    public String acceptOrder(@PathVariable Long id, @CookieValue("user_login") String login) {

        Long currentDriverId = orderServices.getCustomerIdByLogin(login);


        orderRepo.findById(id).ifPresent(order -> {
            order.setDriverId(currentDriverId);
            order.setStatus("DELIVERED");
            orderRepo.save(order);
        });

        return "redirect:/Drivers";
    }
}
