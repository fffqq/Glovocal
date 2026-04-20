package com.example.demo.studentas.Order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private String itemName;
    private Double price;
    private Integer quantity;


public OrderItem(Long id,Long orderId,String itemName,Double price,Integer quantity){
    this.id=id;
    this.orderId=orderId;
    this.itemName=itemName;
    this.price=price;
    this.quantity=quantity;
}


}

