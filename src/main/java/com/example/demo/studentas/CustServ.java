package com.example.demo.studentas;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.http.Cookie;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CustServ {

    private final CustomerRepo customerRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public CustServ(CustomerRepo customerRepo, BCryptPasswordEncoder passwordEncoder) {
        this.customerRepo = customerRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public void PostCust(Customer customer) {
        Optional<Customer> customerOptional = customerRepo.findByEmail(customer.getEmail());
        Optional<Customer> customerOptional2=customerRepo.findByLogin(customer.getLogin());
        if (customerOptional.isPresent()||customerOptional2.isPresent()) {
            throw new IllegalStateException("Email was taken or login");
        }
        if (customer.getLogin()==null||customer.getLogin().isEmpty()||
                customer.getPassword()==null||customer.getPassword().isEmpty()||
                customer.getEmail()==null||customer.getEmail().isEmpty()) {
            throw new IllegalStateException("Email,login or password are empty");
        }
        String passwordEnc=passwordEncoder.encode(customer.getPassword());
        customer.setPassword(passwordEnc);
        customerRepo.save(customer);
    }

    public void deleteCust(Long customerId) {
        boolean exist = customerRepo.existsById(customerId);
        if (!exist) {
            throw new IllegalStateException("Customer with id " + customerId + " does not exist");
        }
        customerRepo.deleteById(customerId);
    }

    @Transactional
    public void putCust(Long customerId, Customer customer) {
        Customer customerReal = customerRepo.findById(customerId)
                .orElseThrow(() -> new IllegalStateException("Customer with id " + customerId + " does not exist"));

        if (customer.getLogin() == null || customer.getLogin().isEmpty() ||
                customer.getEmail() == null || customer.getEmail().isEmpty() ||
                customer.getPassword() == null || customer.getPassword().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "PUT requires all fields: login, email, and password");
        }

        if (!customer.getLogin().equals(customerReal.getLogin()) && customerRepo.existsByLogin(customer.getLogin())) {
            throw new IllegalStateException("Login taken");
        }

        customerReal.setLogin(customer.getLogin());
        customerReal.setEmail(customer.getEmail());
        customerReal.setPassword(passwordEncoder.encode(customer.getPassword()));
    }

    @Transactional
    public void patchCust(Long customerId, Customer customer) {
        Customer customerReal = customerRepo.findById(customerId)
                .orElseThrow(() -> new IllegalStateException("Customer with id " + customerId + " does not exist"));

        if (customer.getLogin() != null && !customer.getLogin().isEmpty()) {
            if (!customer.getLogin().equals(customerReal.getLogin()) && customerRepo.existsByLogin(customer.getLogin())) {
                throw new IllegalStateException("Login " + customer.getLogin() + " is already taken");
            }
            customerReal.setLogin(customer.getLogin());
        }
        if (customer.getEmail() != null && !customer.getEmail().isEmpty()) {
            if (!customer.getEmail().equals(customerReal.getEmail()) && customerRepo.existsByEmail(customer.getEmail())) {
                throw new IllegalStateException("Email " + customer.getEmail() + " is already taken");
            }
            customerReal.setEmail(customer.getEmail());
        }

        if (customer.getPassword() != null && !customer.getPassword().isEmpty()) {
            String passwordEnc = passwordEncoder.encode(customer.getPassword());
            customerReal.setPassword(passwordEnc);
        }
    }

    /*public List<Cookie> authorized(String login,String password) {
        Customer CheckCust=customerRepo.findByLogin(login)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Wrong Login"));
        String passwordCheckCust = CheckCust.getPassword();
        if(passwordCheckCust!=null&&!passwordCheckCust.isEmpty()){

            Cookie loginCookie=new Cookie("user_login",CheckCust.getLogin());
            loginCookie.setMaxAge(60*60*24);
            loginCookie.setPath("/");

            Cookie passwordCookie=new Cookie("user_password",CheckCust.getPassword());
            passwordCookie.setMaxAge(60*60*24);
            passwordCookie.setPath("/");

            if(passwordEncoder.matches(password,passwordCheckCust)){
                return List.of(loginCookie,passwordCookie);
            }
            else
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Wrong password");
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Fill the password Box");
        }
*/


   public boolean authoriz(String login,String password) {
       Customer CheckCust=customerRepo.findByLogin(login)
               .orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Wrong Login"));
       if(passwordEncoder.matches(password,CheckCust.getPassword())){
           return true;
       }
       else
           return false;
   }


}

