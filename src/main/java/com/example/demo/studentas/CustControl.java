package com.example.demo.studentas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:63343", allowCredentials = "true")
@RequestMapping(path="api/v1/glovo")
public class CustControl {
    private final CustServ custServ;
    @Autowired
    public CustControl(CustServ custServ) {
        this.custServ = custServ;
    }
    @PostMapping
    public void PostCust(@RequestBody Customer customer){custServ.PostCust(customer);}
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
    @PostMapping(path = "login")
    public ResponseEntity<String> loginauth(@RequestBody Customer auth) {
        boolean aut=custServ.authorized(auth.getLogin(),auth.getPassword());
        if(aut){
            return ResponseEntity.ok("Authorized");
        }
        else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Login or password");

    }




}