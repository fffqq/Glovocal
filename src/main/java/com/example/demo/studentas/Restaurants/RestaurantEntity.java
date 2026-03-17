package com.example.demo.studentas.Restaurants;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestaurantEntity {
    private Long id;
    private String name;
    private String address;
    private String description;

    public RestaurantEntity(){
        }
    public RestaurantEntity(Long id,String name,String addres,String description){
        this.id=id;
        this.address=addres;
        this.description=description;
        this.name=name;
    }

}