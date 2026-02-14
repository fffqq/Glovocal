package com.example.demo.studentas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/glovo")
public class CustControl {
    private final CustServ custServ;
    @Autowired
    public CustControl(CustServ custServ) {
        this.custServ = custServ; //don't use new
    }
    @GetMapping


    @PostMapping
    public void regNewCust(@RequestBody Customer customer){
        custServ.addNewCust(customer);
    }
    @DeleteMapping(path = "{customerId}")
    public void deleteCust(@PathVariable("customerId") Long customerId) {
        custServ.deleteCust(customerId);
    }
    @PutMapping(path = "{customerId}")
    public void putCust(@PathVariable("customerId") Long customerId,@RequestBody Customer customer) {
        custServ.putCust(customerId,customer);
    }
    @PatchMapping(path = "{customerId}")
    public void patchCust(@PathVariable("customerId") Long customerId,@RequestBody Customer customer) {
        custServ.patchCust(customerId,customer);
    }
    @GetMapping(path = "{customerEmail}")
    public Customer getCust(@PathVariable("customerEmail") String customerEmail) {
        return custServ.getCust(customerEmail);
    }


}