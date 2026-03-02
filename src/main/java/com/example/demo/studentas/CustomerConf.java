package com.example.demo.studentas;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
public class CustomerConf {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(org.springframework.security.config.annotation.web.builders.HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Отключаем CSRF, чтобы работали POST запросы из JS
                .cors(org.springframework.security.config.Customizer.withDefaults()) // Разрешаем настройки @CrossOrigin из контроллера
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Разрешаем доступ ко всем эндпоинтам (для тестов)
                );
        return http.build();
    }

    @Bean
    CommandLineRunner commandLineRunner(CustomerRepo repo) {
        return args -> {
            Customer Pindor = new Customer("fdds", passwordEncoder().encode("pipa"), "va2007321@gmdsaail.com");
            Customer Pindos = new Customer("fddfds", passwordEncoder().encode("pipa"), "va2007sdsa321@gmail.com");
            Customer Podnos = new Customer("Bo", passwordEncoder().encode("pipa"), "va20073dsa1@gmail.com");
            repo.saveAll(List.of(Pindos, Podnos, Pindor));
        };

    }
}

