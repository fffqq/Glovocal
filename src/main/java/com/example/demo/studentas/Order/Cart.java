package com.example.demo.studentas.Order;



import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<OrderItem> items = new ArrayList<>();

    public void addItem(OrderItem item){
        for (OrderItem i : items) {
            if (i.getItemName().equals(item.getItemName())) {
                i.setQuantity(i.getQuantity() + 1);
                return;
            }
        }
        items.add(item);
    }
    public List<OrderItem> getItems() { return items; }
    public Double getTotalPrice() {
        return items.stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum();
    }


}

