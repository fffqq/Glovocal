package com.example.demo.studentas.Restaurants;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
@Configuration
public class RestaurantsConfig {
    @Bean
    CommandLineRunner commandLineRunner1(RestaurantRepo repo) {
        return args -> {
            RestaurantEntity mcdonalds = new RestaurantEntity();
            mcdonalds.setName("Mcdonalds");
            mcdonalds.setAddress("Kareivu g.15");
            mcdonalds.setDescription("Rygalovka american food");
            repo.create(mcdonalds);
            System.out.println(mcdonalds.getId()+"Id Restorana");
            List<RestaurantEntity> found = repo.findallByName(mcdonalds.getName());
            found.forEach(r -> System.out.println("Найден: " + r.getName() + " по адресу " + r.getAddress()));
        };
    }
}
