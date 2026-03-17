package com.example.demo.studentas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebSecurity
public class CustomerConf implements WebMvcConfigurer {
    @Autowired
    private FilterCoockie filterCoockie;
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    /*@Bean
    public SecurityFilterChain filterChain(org.springframework.security.config.annotation.web.builders.HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(org.springframework.security.config.Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }*/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(filterCoockie)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/login",
                        "/process-login",
                        "/Registration",
                        "/Reg-Authorize",
                        "/api/v1/glovo",
                        "/api/v1/glovo/login",
                        "/css/**",
                        "/js/**",
                        "/error",
                        "/*.html",
                        "/*.js"
                );
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
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .csrf(csrf -> csrf.disable())


                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )


                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable());

        return http.build();
    }
}

