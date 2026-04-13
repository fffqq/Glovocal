package com.example.demo.studentas.MenuItems;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepo extends JpaRepository<Menu,Long> {
    List<Menu> findAllById(Long id);
}
