package com.example.demo.studentas;
import jakarta.persistence.*;

@Entity
@Table(name ="customer")//куда в postgres сохранять
public class Customer {

    @Id
    @SequenceGenerator(
            name="customer_sequence",sequenceName ="customer_sequence",allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,generator = "customer_sequence"
    )
    private Long id;
    @Column(name = "login", nullable = false, unique = true)
    private String login;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "email",nullable = false,unique = true)
    private String email;

    public Customer(Long id, String login, String password, String email){//constructor
        this.id=id;
        this.login=login;
        this.password=password;
        this.email=email;
    }
    public Customer(String login, String password, String email){//constructor without id,cause
        // id will generate by sequence
        this.login=login;
        this.password=password;
        this.email=email;
    }

    public Customer() {

    }
    public void setLogin(String login){this.login=login;}//seters

    public void setEmail(String email){this.email=email;}//setters

    public void setPassword(String password){this.password=password;}//getters

    public String getLogin(){return login;}

    public String getEmail(){return email;}

    public String getPassword(){return password;}

    public Long getId(){return id;}//getters




}
