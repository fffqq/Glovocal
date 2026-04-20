package com.example.demo.studentas.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<OrderEntity, Long> {
List<OrderEntity>findAllByCustomerId(Long id);
List<OrderEntity>findAll();
}
