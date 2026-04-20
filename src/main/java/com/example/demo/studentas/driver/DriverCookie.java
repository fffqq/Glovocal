package com.example.demo.studentas.driver;

import com.example.demo.studentas.Customer;
import com.example.demo.studentas.CustomerRepo;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

@Component
@EnableWebSecurity
public class DriverCookie implements HandlerInterceptor {

    @Autowired
    private CustomerRepo customerRepo;
    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler)throws Exception {
        Cookie[] cookies = request.getCookies();
        String logCookie = null;
        String pasCookie = null;
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("user_login")) {
                    logCookie = c.getValue();
                }
                if (c.getName().equals("user_password")) {
                    pasCookie = c.getValue();
                }
            }
        }
        if (logCookie!=null && pasCookie!=null) {
            Optional<Customer> chekCust = customerRepo.findByLogin(logCookie);

            if (chekCust.isPresent() && chekCust.get().getPassword().equals(pasCookie)
                    && ("DRIVER".equals(chekCust.get().getRole())||"ADMIN".equals(chekCust.get().getRole()))) {
                return true;
            }
        }
        response.sendRedirect("/login");
        return false;
    }

}



