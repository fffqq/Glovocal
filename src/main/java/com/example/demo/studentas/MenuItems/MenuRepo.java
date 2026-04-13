package com.example.demo.studentas.MenuItems;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuRepo extends JpaRepository<Menu,Long> {
    List<Menu> findAllById(Long id);
    List<Menu> findAllByRestaurantId(Long id);
    Optional<Menu> findByName(String name);
}
