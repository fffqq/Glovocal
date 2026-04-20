package com.example.demo.studentas.Admins;

import com.example.demo.studentas.MenuItems.Menu;
import com.example.demo.studentas.MenuItems.MenuRepo;
import com.example.demo.studentas.Order.OrderEntity;
import com.example.demo.studentas.Order.OrderRepo;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

@Service
public class AdminServices {
    public final MenuRepo Mrepo;
    private final OrderRepo orderRepo;
    public  AdminServices(MenuRepo Mrepo,OrderRepo orderRepo){
        this.Mrepo=Mrepo;
        this.orderRepo=orderRepo;
    }
    public Menu createItem(String name, Double price, Long resId){
        if (Mrepo.findByName(name).isPresent()||price==null) {
            throw new IllegalStateException("Error: this position already exists or no value");
        }
            Menu newItem=new Menu();
            newItem.setName(name);
            newItem.setPrice(price);
            newItem.setRestaurantId(resId);
            return Mrepo.save(newItem);


    }
    public void updateItem(Long itemId, String name, Double price) {
        Menu item = Mrepo.findById(itemId)
                .orElseThrow(() -> new IllegalStateException("Item not found"));
        item.setName(name);
        item.setPrice(price);
        Mrepo.save(item);
    }
    public List<OrderEntity> getAllOrders() {

        return orderRepo.findAll();
    }
}
