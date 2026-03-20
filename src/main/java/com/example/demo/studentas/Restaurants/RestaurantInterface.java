package com.example.demo.studentas.Restaurants;

import java.util.List;
import java.util.Optional;

public interface RestaurantInterface  {
    void create(RestaurantEntity restaurant);
    List<RestaurantEntity>findallByName(String name);
    void deleteById(Long id);
    Optional <RestaurantEntity> findById(Long id);
    List<RestaurantEntity>findAll();

}
