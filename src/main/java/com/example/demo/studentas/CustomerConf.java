package com.example.demo.studentas;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CustomerConf  {
    @Bean
    CommandLineRunner commandLineRunner(CustomerRepo repo){
        return args -> {
            Customer Pindor=new Customer( "fdds","pizda","va2007321@gmdsaail.com");
            Customer Pindos=new Customer( "fddfds","pizda","va2007sdsa321@gmail.com");
            Customer Podnos=new Customer( "Bo","pizda","va20073dsa1@gmail.com");
            repo.saveAll(List.of(Pindos,Podnos,Pindor));
        };

    }
}
