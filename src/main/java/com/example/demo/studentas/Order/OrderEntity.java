package com.example.demo.studentas.Order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long customerId;
    private Long restaurantId;
    private Double totalPrice;
    private String status;
    private String customerAddres;
    @Transient
    private String restaurantName;
    private Long driverId;

    public OrderEntity(Long driverId,Long id, Long customerId, Long restaurantId, Double totalPrice, String status, String customerAddres) {
        this.id = id;
        this.customerId=customerId;
        this.restaurantId=restaurantId;
        this.totalPrice=totalPrice;
        this.status=status;
        this.customerAddres=customerAddres;
        this.driverId=driverId;

    }

}
