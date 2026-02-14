package com.example.demo.studentas;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustServ {

    private final CustomerRepo customerRepo;

    @Autowired
    public CustServ(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    public void addNewCust(Customer customer) {
        Optional<Customer> customerOptional = customerRepo.findByEmail(customer.getEmail());
        if (customerOptional.isPresent()) {
            throw new IllegalStateException("Email was taken");
        }
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
        Customer customerReal = customerRepo.findById(customerId).orElseThrow(() -> new IllegalStateException("Customer with id "
                + customerId + " does not exist"));
        customerReal.setLogin(customer.getLogin());
        customerReal.setEmail(customer.getEmail());
        customerReal.setPassword(customer.getPassword());
    }

    @Transactional
    public void patchCust(Long customerId, Customer customer) {
        Customer customerReal = customerRepo.findById(customerId).
                orElseThrow(() -> new IllegalStateException("Customer with id "
                        + customerId + " does not exist"));

        if (customer.getLogin() != null) {
            customerReal.setLogin(customer.getLogin());
        }

        if (customer.getEmail() != null) {
            customerReal.setEmail(customer.getEmail());
        }
        if (customer.getPassword() != null) {
            customerReal.setPassword(customer.getPassword());
        }

    }

    public Customer getCust(String customerEmail) {
        return customerRepo.findByEmail(customerEmail).
                orElseThrow(() -> new IllegalStateException("Customer with Email "
                        + customerEmail + " does not exist"));
    }
}

