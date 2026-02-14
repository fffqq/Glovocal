package com.example.demo.studentas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

@Repository
//because need data acces ^
//writing with what objects this interface will work,with customer and ID
public interface CustomerRepo extends JpaRepository<Customer,Long> {
    @Query("SELECT c from Customer c where c.email=?1")
    Optional<Customer> findByEmail(String email);

    // List<Customer> login(String login);
}

