package com.example.demo.studentas;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ControllerNotByRest {
    private final CustServ custServ;
    @Autowired
    public ControllerNotByRest(CustServ custServ) {
        this.custServ = custServ;
    }
    @PostMapping("/process-login")
    public String logautho(Customer authcok, HttpServletResponse response) {

        List<Cookie> cookies = custServ.authorized(authcok.getLogin(), authcok.getPassword());
        if (cookies.isEmpty()) {

            return "redirect:/login";
        }

        for (Cookie cookie : cookies) {
            response.addCookie(cookie);
        }
        return "redirect:/Restaurants";
    }
}