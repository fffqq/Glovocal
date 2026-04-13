package com.example.demo.studentas.MenuItems;

import com.example.demo.studentas.Restaurants.RestaurantEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "menu")
@Getter
@Setter
@NoArgsConstructor
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;

    public Menu(String name, Double price, Long restaurantId) {
        this.name = name;
        this.price = price;
        this.restaurantId = restaurantId;
    }
}