package com.example.demo.studentas.Admins;

import com.example.demo.studentas.MenuItems.Menu;
import com.example.demo.studentas.MenuItems.MenuRepo;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service
public class AdminServices {
    public final MenuRepo Mrepo;
    public  AdminServices(MenuRepo Mrepo){
        this.Mrepo=Mrepo;
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
}
